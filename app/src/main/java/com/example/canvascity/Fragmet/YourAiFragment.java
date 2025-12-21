package com.example.canvascity.Fragmet;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.canvascity.Activity.ProfileActivity;
import com.example.canvascity.R;

import java.util.HashMap;import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class YourAiFragment extends Fragment {
    private EditText etEvent;
    private Button btnAiSuggest;
    private TextView tvAiResponse;
    private ListView listSuggestions;
    private ArrayAdapter<String> suggestionAdapter;
    private ArrayList<String> suggestionList = new ArrayList<>();

    private HashMap<String, String> qaMap = new HashMap<>();

    public YourAiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ✅ VERY IMPORTANT for fragment menu
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_your_ai, container, false);
        listSuggestions = view.findViewById(R.id.listSuggestions);

        suggestionAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                suggestionList
        );

        listSuggestions.setAdapter(suggestionAdapter);
        listSuggestions.setOnItemClickListener((parent, view1, position, id) -> {
            String selected = suggestionList.get(position);
            etEvent.setText(selected);
            etEvent.setSelection(selected.length());
            listSuggestions.setVisibility(View.GONE);
        });

        etEvent = view.findViewById(R.id.etEvent);
        btnAiSuggest = view.findViewById(R.id.btnAiSuggest);
        tvAiResponse = view.findViewById(R.id.tvAiResponse);

        tvAiResponse.setMovementMethod(new ScrollingMovementMethod());

        initQA(); // load your preset questions

        btnAiSuggest.setOnClickListener(v -> {
            String userInput = etEvent.getText().toString().trim();
            if(userInput.isEmpty()){
                tvAiResponse.setText("Please enter your event or question!");
                return;
            }

            String answer = getClosestAnswer(userInput); // will add fuzzy logic next
            tvAiResponse.setText(answer);
        });
        etEvent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showSuggestions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }
    private void showSuggestions(String query) {
        suggestionList.clear();

        if (query.length() < 2) {
            listSuggestions.setVisibility(View.GONE);
            return;
        }

        for (String question : qaMap.keySet()) {
            if (question.toLowerCase().contains(query.toLowerCase())) {
                suggestionList.add(question);
            }
        }

        if (suggestionList.isEmpty()) {
            listSuggestions.setVisibility(View.GONE);
        } else {
            listSuggestions.setVisibility(View.VISIBLE);
            suggestionAdapter.notifyDataSetChanged();
        }
    }

    private String getClosestAnswer(String userInput) {
        String closestQuestion = null;
        int minDistance = Integer.MAX_VALUE;

        for (String question : qaMap.keySet()) {
            int distance = levenshteinDistance(userInput.toLowerCase(), question.toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestQuestion = question;
            }
        }

        if(minDistance > 30){ // adjust threshold if needed
            return "Sorry, I don't have a suggestion for that yet.";
        }

        return qaMap.get(closestQuestion);
    }

    private int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for(int i = 0; i <= s1.length(); i++) dp[i][0] = i;
        for(int j = 0; j <= s2.length(); j++) dp[0][j] = j;

        for(int i = 1; i <= s1.length(); i++) {
            for(int j = 1; j <= s2.length(); j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[s1.length()][s2.length()];
    }
    private void initQA() {
        // 1-20
        qaMap.put("Should I wear jeans or chinos today?", "Jeans are versatile for casual looks, chinos are slightly smarter.");
        qaMap.put("T-shirt or shirt?", "T-shirt for casual, shirt for smart-casual or office.");
        qaMap.put("What color should I wear today?", "Neutral colors are safe: white, black, gray, beige. Add a pop of color if you like.");
        qaMap.put("Can I wear stripes with stripes?", "Mix stripes only if one pattern is subtle; otherwise pair with solids.");
        qaMap.put("Sneakers or boots?", "Sneakers for casual, boots for cooler weather or smart-casual.");
        qaMap.put("Is this outfit office appropriate?", "Stick to muted colors and simple patterns. Avoid loud graphics.");
        qaMap.put("Can I wear black and navy together?", "Yes, keep accessories minimal for a subtle look.");
        qaMap.put("What shoes match blue jeans?", "White sneakers, brown loafers, or casual boots.");
        qaMap.put("Can I wear a hoodie with chinos?", "Yes, it gives a smart-casual relaxed vibe.");
        qaMap.put("What jacket goes with a dress?", "Denim or cropped leather jackets work best.");
        qaMap.put("Should I tuck in my shirt?", "Tuck for formal or smart-casual looks, untuck for casual.");
        qaMap.put("Can I wear white shoes with black pants?", "Yes, it creates a modern contrast.");
        qaMap.put("What accessories go with a plain T-shirt?", "A watch, bracelet, or simple necklace elevates the look.");
        qaMap.put("Can I wear a belt with sneakers?", "Yes, keep it casual and match outfit colors.");
        qaMap.put("Is layering necessary in spring?", "Yes, use light jackets, shirts, or cardigans.");
        qaMap.put("What colors go with beige?", "White, brown, navy, or muted green.");
        qaMap.put("Can I wear all black?", "Yes, add textures to avoid a flat look.");
        qaMap.put("How do I mix patterns?", "Use one bold pattern with solids or subtle patterns.");
        qaMap.put("Is it okay to wear shorts to a party?", "Casual day parties: yes. Evening/formal: no.");
        qaMap.put("What color shirt with gray pants?", "White, black, navy, or soft pastels.");

        // 21-40
        qaMap.put("Can I wear socks with sandals?", "Generally no unless it’s a sporty look.");
        qaMap.put("What bag matches casual outfits?", "Canvas totes, backpacks, or small crossbody bags.");
        qaMap.put("How do I style a plain hoodie?", "Pair with jeans, joggers, or a casual jacket.");
        qaMap.put("Can I wear leather jacket in summer?", "Light jackets work for evenings or cooler days.");
        qaMap.put("Should I match my belt with shoes?", "Yes, it’s a classic style tip.");
        qaMap.put("What outfit is good for a date?", "Smart-casual: nice jeans/trousers, clean shirt, stylish shoes.");
        qaMap.put("Can I wear patterns together?", "Yes, balance bold with subtle.");
        qaMap.put("What color blazer is versatile?", "Navy or gray blazers work for most occasions.");
        qaMap.put("Can I wear sneakers with a suit?", "Yes, for modern casual look. Formal: dress shoes.");
        qaMap.put("Should I wear a hat?", "Optional, keep it simple and coordinate with outfit.");
        qaMap.put("What t-shirt color goes with black jeans?", "White, gray, or muted colors.");
        qaMap.put("Can I wear denim on denim?", "Yes, use different shades: dark jacket + light jeans.");
        qaMap.put("Is it okay to wear bright colors?", "Yes, in moderation. Balance with neutral items.");
        qaMap.put("What shoes go with chinos?", "Loafers, casual boots, or sneakers.");
        qaMap.put("Can I wear a scarf in summer?", "Light scarves work as accessories.");
        qaMap.put("What color shirt with khaki pants?", "White, navy, gray, or muted earth tones.");
        qaMap.put("Is layering necessary for winter?", "Yes, base layers, sweaters, jackets, and coats.");
        qaMap.put("Can I wear shorts with sneakers?", "Yes, classic casual look.");
        qaMap.put("What jacket matches black jeans?", "Denim, bomber, leather, or casual blazers.");
        qaMap.put("Should I match socks with pants or shoes?", "Match with pants or use color for style pop.");

        // 41-60
        qaMap.put("What colors go well together?", "Neutral colors match everything; complementary colors add contrast.");
        qaMap.put("Can I wear a watch with casual outfits?", "Yes, simple or leather strap watches work.");
        qaMap.put("Should I wear a tie to casual events?", "No, save ties for formal/semi-formal events.");
        qaMap.put("Can I wear a blazer with jeans?", "Yes, it’s a smart-casual classic look.");
        qaMap.put("What color shoes go with gray pants?", "Black, brown, or burgundy.");
        qaMap.put("Can I wear a vest?", "Yes, for layering or smart-casual.");
        qaMap.put("What color T-shirt with blue jeans?", "White, gray, black, or pastels.");
        qaMap.put("Is it okay to wear leather boots with jeans?", "Absolutely, casual or smart-casual.");
        qaMap.put("Can I wear shorts with a button-up shirt?", "Yes, for casual summer looks.");
        qaMap.put("Should I roll up my sleeves?", "Yes, for relaxed look. Keep neat for smart-casual.");
        qaMap.put("What colors go with white?", "Black, navy, gray, pastels, or bright colors.");
        qaMap.put("Can I wear a hoodie under a jacket?", "Yes, popular layered casual style.");
        qaMap.put("Should I wear sunglasses indoors?", "No, unless for a fashion shoot.");
        qaMap.put("What pants go with a graphic T-shirt?", "Jeans, chinos, or casual shorts.");
        qaMap.put("Can I wear brown shoes with black pants?", "Casual okay; formal stick to black shoes.");
        qaMap.put("What shirt goes with green pants?", "Neutral colors like white, black, beige, gray.");
        qaMap.put("Can I wear white pants in winter?", "Yes, pair with darker tops/jackets.");
        qaMap.put("Should I wear socks with dress shoes?", "Yes, match socks with pants color.");
        qaMap.put("Can I wear a tie with a casual shirt?", "Generally no; only semi-smart occasions.");
        qaMap.put("What color jacket goes with blue jeans?", "Denim, black, brown, gray, olive.");

        // 61-80
        qaMap.put("Can I wear a polo shirt with shorts?", "Yes, casual summer look.");
        qaMap.put("What shoes with white T-shirt?", "Sneakers, loafers, or casual boots.");
        qaMap.put("Can I wear a hoodie with shorts?", "Yes, casual and sporty look.");
        qaMap.put("What jacket goes with chinos?", "Bomber, denim, or blazer depending on style.");
        qaMap.put("Can I wear leather sneakers with jeans?", "Yes, modern casual look.");
        qaMap.put("What color belt with brown shoes?", "Brown belt matching shoe color is classic.");
        qaMap.put("Can I wear a denim jacket with black jeans?", "Yes, works well for casual outings.");
        qaMap.put("Should I wear patterned socks?", "Yes, adds personality; keep subtle for formal.");
        qaMap.put("Can I wear a blazer in summer?", "Yes, lightweight fabrics like linen work best.");
        qaMap.put("What colors match navy pants?", "White, gray, beige, pastels, or muted tones.");
        qaMap.put("Can I wear a scarf with a T-shirt?", "Yes, for style, light fabric recommended.");
        qaMap.put("Should I wear a hat with casual outfit?", "Optional, but can elevate casual looks.");
        qaMap.put("What shoes match gray pants?", "Black, brown, burgundy, or white sneakers.");
        qaMap.put("Can I wear shorts to office?", "Generally no; stick to pants for office.");
        qaMap.put("Should I wear a watch with formal outfit?", "Yes, classic watches complete the look.");
        qaMap.put("Can I wear leather jacket with dress shirt?", "Yes, for smart-casual style.");
        qaMap.put("What t-shirt colors go with chinos?", "White, gray, navy, pastels.");
        qaMap.put("Can I wear bright shoes with neutral outfit?", "Yes, they can be the statement piece.");
        qaMap.put("Should I wear a tie with a blazer?", "Optional; depends on formality.");
        qaMap.put("Can I wear denim shorts in winter?", "No, better for summer or indoor casual looks.");
// 81-100
        qaMap.put("Can I wear a maxi dress casually?", "Yes, pair with sneakers or sandals for casual look.");
        qaMap.put("What colors go with red pants?", "Neutrals like white, black, gray, or navy work best.");
        qaMap.put("Can I wear a blazer with shorts?", "Only for very casual or fashion-forward looks.");
        qaMap.put("What shoes match beige chinos?", "Brown loafers, sneakers, or casual boots.");
        qaMap.put("Can I wear a crop top with high-waist jeans?", "Yes, it’s a trendy and flattering combo.");
        qaMap.put("Should I wear black with navy?", "Yes, but keep accessories minimal.");
        qaMap.put("What T-shirt color with green pants?", "White, beige, gray, or navy.");
        qaMap.put("Can I wear boots in summer?", "Yes, lightweight boots work, but avoid heavy leather.");
        qaMap.put("Should I wear layers indoors?", "Only if temperature fluctuates; keep it light.");
        qaMap.put("Can I mix prints in one outfit?", "Yes, balance bold and subtle prints carefully.");

// 101-120
        qaMap.put("What jacket goes with khaki pants?", "Denim, bomber, or casual blazer.");
        qaMap.put("Can I wear sneakers with a dress?", "Yes, for casual or daytime outings.");
        qaMap.put("What color shoes with navy blazer?", "Brown or black shoes depending on occasion.");
        qaMap.put("Should I wear patterned socks with suit?", "Subtle patterns add personality without overdoing.");
        qaMap.put("Can I wear pastel colors in winter?", "Yes, pair with neutrals for balance.");
        qaMap.put("What shirt with light gray pants?", "White, black, navy, or pastel shades.");
        qaMap.put("Can I wear leather jacket with T-shirt?", "Yes, classic casual combo.");
        qaMap.put("What accessories with formal outfit?", "Watch, belt, subtle cufflinks, minimal jewelry.");
        qaMap.put("Can I wear sneakers to office?", "Depends on office dress code; smart-casual is okay.");
        qaMap.put("Should I roll sleeves for formal shirt?", "No, keep sleeves buttoned for formal look.");
        qaMap.put("What T-shirt with black jeans?", "White, gray, pastel, or muted colors.");
        qaMap.put("Can I wear denim jacket with dress?", "Yes, great casual style.");
        qaMap.put("Should I match belt and shoes color?", "Yes, classic tip for polished look.");
        qaMap.put("What shoes with chinos?", "Loafers, casual boots, sneakers depending on style.");
        qaMap.put("Can I wear bright top with neutral bottom?", "Yes, balances outfit nicely.");
        qaMap.put("What bag with formal outfit?", "Leather bag or structured briefcase.");
        qaMap.put("Can I wear leather boots with suit?", "Only for smart-casual, not formal events.");
        qaMap.put("Should I wear watch with casual outfit?", "Yes, adds style subtly.");
        qaMap.put("What jacket for summer evening?", "Light bomber, linen blazer, or denim jacket.");
        qaMap.put("Can I wear all white outfit?", "Yes, ensure fabrics and textures vary for dimension.");

// 121-140
        qaMap.put("Can I wear shorts in office?", "No, stick to pants or smart-casual alternatives.");
        qaMap.put("What color shoes with white pants?", "Brown, tan, or white sneakers for casual look.");
        qaMap.put("Should I wear tie with colored shirt?", "Optional, depends on formality.");
        qaMap.put("Can I wear hoodie to office?", "No, keep hoodies casual.");
        qaMap.put("What jacket with printed shirt?", "Solid color jacket to avoid clash.");
        qaMap.put("Can I wear sneakers with chinos?", "Yes, casual and comfortable.");
        qaMap.put("What T-shirt with pastel pants?", "White, beige, gray, or muted tones.");
        qaMap.put("Can I wear denim shorts with T-shirt?", "Yes, classic summer casual look.");
        qaMap.put("Should I wear hat with suit?", "No, hats generally casual or special events.");
        qaMap.put("What accessories for casual outfit?", "Watch, bracelet, small necklace or hat.");
        qaMap.put("Can I wear stripes with plain pants?", "Yes, stripes pair well with solids.");
        qaMap.put("What shoes for casual summer outfit?", "Sneakers, sandals, loafers.");
        qaMap.put("Can I layer T-shirt under shirt?", "Yes, great for casual layered look.");
        qaMap.put("What color jacket with black jeans?", "Denim, leather, bomber, or gray jackets.");
        qaMap.put("Can I wear black boots with brown belt?", "Better to match belt and shoes for cohesive look.");
        qaMap.put("Should I wear long socks with shorts?", "No, go for no-show or ankle socks.");
        qaMap.put("What shirt with dark jeans?", "White, black, gray, pastel, or muted colors.");
        qaMap.put("Can I wear scarf in summer?", "Yes, lightweight scarf for style.");
        qaMap.put("Should I wear bracelet with formal outfit?", "Minimalist bracelets are okay if subtle.");
        qaMap.put("What shoes with striped pants?", "Solid color shoes to balance the outfit.");

// 141-165
        qaMap.put("Can I wear a leather jacket in rain?", "Not recommended unless waterproof.");
        qaMap.put("What T-shirt color with navy pants?", "White, gray, beige, or muted tones.");
        qaMap.put("Can I wear patterned shirt with patterned pants?", "Yes, only if one pattern is subtle.");
        qaMap.put("Should I wear sneakers with blazer?", "Yes, for smart-casual look.");
        qaMap.put("Can I wear tie with vest?", "Yes, formal or semi-formal occasions.");
        qaMap.put("What jacket for casual outing?", "Denim, bomber, hoodie, or light jacket.");
        qaMap.put("Can I wear black jeans in summer?", "Yes, but lightweight fabrics are better.");
        qaMap.put("Should I match shoes with bag?", "Yes, classic style tip.");
        qaMap.put("Can I wear a plain hoodie with printed pants?", "Yes, balances the outfit.");
        qaMap.put("What accessories with maxi dress?", "Simple necklace, small bag, or sandals.");
        qaMap.put("Can I wear boots with shorts?", "Generally no, unless fashion-forward style.");
        qaMap.put("Should I roll cuffs of jeans?", "Yes, casual style, shows shoes nicely.");
        qaMap.put("Can I wear sneakers with formal shirt?", "Yes, smart-casual look works.");
        qaMap.put("What shirt with beige chinos?", "White, light blue, gray, pastel colors.");
        qaMap.put("Can I wear blazer over hoodie?", "Yes, trendy layered look.");
        qaMap.put("Should I wear necklace with T-shirt?", "Optional, subtle chain works.");
        qaMap.put("What shoes with patterned dress?", "Solid color flats, heels, or sneakers.");
        qaMap.put("Can I wear leather jacket with dress pants?", "Yes, smart-casual look.");
        qaMap.put("Should I wear socks with loafers?", "Optional, no-show socks recommended.");
        qaMap.put("What color T-shirt with brown pants?", "White, beige, gray, or navy.");
        qaMap.put("Can I wear hoodie under blazer?", "Yes, modern casual look.");
        qaMap.put("Should I match watch strap with shoes?", "Yes, subtle matching enhances style.");
        qaMap.put("Can I wear boots with slim jeans?", "Yes, classic casual look.");
        qaMap.put("What jacket with summer dress?", "Denim jacket, light cardigan, or blazer.");
        qaMap.put("Can I wear sneakers with skirt?", "Yes, casual and trendy.");
        qaMap.put("Should I wear bracelet with hoodie?", "Yes, subtle bracelets are okay.");
        qaMap.put("Can I wear bright top with patterned pants?", "Yes, balance with neutral shoes and accessories.");
        qaMap.put("What shoes with cargo pants?", "Sneakers, casual boots, or loafers.");
        qaMap.put("Can I wear denim jacket in winter?", "Yes, layer with sweaters or hoodies.");
        qaMap.put("Should I wear necklace with dress shirt?", "Optional, subtle pendant works.");
        qaMap.put("Can I wear pastel jacket with dark pants?", "Yes, contrast works well.");
        qaMap.put("What T-shirt color with olive pants?", "White, beige, gray, muted tones.");
        qaMap.put("Can I wear sneakers with suit pants?", "Yes, modern smart-casual look.");
        qaMap.put("Should I match belt with bag?", "Optional, matching shoes is more important.");
        qaMap.put("Can I wear black jacket with blue jeans?", "Yes, classic casual look.");
        qaMap.put("What accessories with blazer?", "Watch, subtle bracelet, pocket square.");
        qaMap.put("Can I wear sneakers with dress pants?", "Yes, for smart-casual occasions.");
        qaMap.put("Should I wear socks with boots?", "Yes, preferably mid or ankle socks.");
        qaMap.put("Can I wear hoodie with suit jacket?", "Yes, fashion-forward casual look.");
        qaMap.put("What shoes with white pants?", "Brown, tan, or white sneakers.");
        qaMap.put("Can I wear leather shoes with jeans?", "Yes, casual or smart-casual.");
        qaMap.put("Should I wear belt with dress pants?", "Yes, classic style tip.");
        qaMap.put("Can I wear patterned shoes with neutral outfit?", "Yes, makes shoes stand out.");
        qaMap.put("What color scarf with black jacket?", "Gray, white, or bright color for contrast.");
        qaMap.put("Can I wear shorts with dress shirt?", "Yes, casual summer look.");
        qaMap.put("Should I wear necklace with hoodie?", "Optional, subtle chain works.");
        qaMap.put("Can I wear boots with patterned pants?", "Yes, keep shoes neutral.");
        qaMap.put("What jacket with striped shirt?", "Solid color jacket to balance outfit.");
        qaMap.put("Can I wear sneakers with hoodie?", "Yes, casual and comfortable.");
        qaMap.put("Should I match bag color with shoes?", "Optional, focus on belt-shoes match first.");
        qaMap.put("Can I wear blazer with jeans and sneakers?", "Yes, classic smart-casual combo.");
        qaMap.put("What accessories with casual T-shirt?", "Watch, bracelet, hat, or small necklace.");
        qaMap.put("Can I wear black shoes with gray pants?", "Yes, classic and formal.");
        qaMap.put("Should I wear patterned socks with casual outfit?", "Yes, subtle patterns work well.");

        // 81-165
        // Continue in same format, covering:
        // Seasonal styles: spring, summer, autumn, winter
        // Casual vs formal events
        // Color coordination (red, blue, green, yellow, pastel)
        // Accessories: belts, watches, bracelets, hats, bags
        // Footwear: sneakers, boots, loafers, dress shoes
        // Outfit combinations: layers, patterns, stripes, solids
        // Occasions: date, office, party, wedding, casual outing
        // Body type suggestions: slim, tall, curvy, petite
        // Wardrobe basics: t-shirts, shirts, jeans, chinos, dresses, skirts
        // Safety nets: "I don't have an exact suggestion, neutral options work"
        // The exact text can be filled by repeating the qaMap.put("question","answer") pattern.
    }

    // ✅ Inflate top 3-dot menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_events, menu); // use your existing menu XML
        super.onCreateOptionsMenu(menu, inflater);
    }

    // ✅ Handle menu clicks (IF–ELSE style)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_profile) {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_report) {
            // Handle report action
            Toast.makeText(getActivity(), "Report clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.menu_logout) {
            showLogoutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ✅ Logout dialog (Fragment-safe)
    private void showLogoutDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) -> {
                    Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
                    // signOutUser(); // if you have a sign-out function
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
