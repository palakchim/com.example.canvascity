package com.example.canvascity.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.canvascity.Activity.ProfileActivity;
import com.example.canvascity.R;

import java.util.ArrayList;
import java.util.HashMap;

public class YourAiFragment extends Fragment {

    // Views
    private EditText etEvent;
    private Button btnAiSuggest;
    private TextView tvAiResponse;
    private ImageView imgGallery;
    private ListView listSuggestions;

    // Data
    private HashMap<String, String> qaMap = new HashMap<>();
    private ArrayList<String> suggestionList = new ArrayList<>();
    private ArrayAdapter<String> suggestionAdapter;

    // Gallery request code
    private static final int PICK_IMAGE = 1001;

    public YourAiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // menu enabled
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_your_ai, container, false);

        // ✅ 1️⃣ Initialize ALL views FIRST
        etEvent = view.findViewById(R.id.etEvent);
        btnAiSuggest = view.findViewById(R.id.btnAiSuggest);
        tvAiResponse = view.findViewById(R.id.tvAiResponse);
        listSuggestions = view.findViewById(R.id.listSuggestions);
        imgGallery = view.findViewById(R.id.imgGallery);

        // ✅ 2️⃣ Initialize data
        initQA();

        // ✅ 3️⃣ Setup suggestion adapter
        suggestionAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                suggestionList
        );
        listSuggestions.setAdapter(suggestionAdapter);
        etEvent.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String query = s.toString().toLowerCase().trim();
                suggestionList.clear();

                if (!query.isEmpty()) {
                    for (String question : qaMap.keySet()) {
                        if (question.toLowerCase().contains(query)) {
                            suggestionList.add(question);
                        }
                    }
                }

                if (suggestionList.isEmpty()) {
                    listSuggestions.setVisibility(View.GONE);
                } else {
                    listSuggestions.setVisibility(View.VISIBLE);
                }

                suggestionAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        // ✅ 4️⃣ List item click
        listSuggestions.setOnItemClickListener((parent, view1, position, id) -> {
            String selected = suggestionList.get(position);
            etEvent.setText(selected);
            etEvent.setSelection(selected.length());
            listSuggestions.setVisibility(View.GONE);
        });

        // ✅ 5️⃣ Button click → AI response
        btnAiSuggest.setOnClickListener(v -> {
            String userInput = etEvent.getText().toString().trim();

            if (userInput.isEmpty()) {
                Toast.makeText(getActivity(), "Please describe your event", Toast.LENGTH_SHORT).show();
                return;
            }


            String answer = getClosestAnswer(userInput); // will add fuzzy logic next
            tvAiResponse.setText(answer);

        });

        // ✅ 6️⃣ Gallery click
        imgGallery.setOnClickListener(v -> openGallery());

        return view;
    }

    // ================= MENU ==================

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_events, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_profile) {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
            return true;

        } else if (item.getItemId() == R.id.menu_report) {
            Toast.makeText(getActivity(), "Report clicked", Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getItemId() == R.id.menu_logout) {
            showLogoutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout", (dialog, which) ->
                        Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // ================= GALLERY ==================

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            Toast.makeText(getActivity(), "Image selected ✔", Toast.LENGTH_SHORT).show();

            // Later you can analyze image for outfit suggestions
        }
    }

    // ================= AI LOGIC ==================

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
        qaMap.put("What color shoes with white pants?", "Brown, tan, or white sneakers work best for a clean look.");
        qaMap.put("What color shirt goes with black jeans?", "White, grey, pastel, or olive look great with black jeans.");
        qaMap.put("Can I wear black with brown?", "Yes, black and brown can look classy if shades are balanced.");
        qaMap.put("What colors go with beige pants?", "White, navy, black, olive, and pastel shades pair well.");
        qaMap.put("Is navy better than black?", "Navy looks softer and more versatile for daytime outfits.");
        qaMap.put("What color top with blue jeans?", "White, black, grey, pastel pink, or light blue work well.");
        qaMap.put("Can I wear white shoes daily?", "Yes, but keep them clean and avoid muddy or rainy days.");
        qaMap.put("What color jacket goes with everything?", "Black, denim blue, or beige jackets are most versatile.");
        qaMap.put("Is all black outfit okay?", "Yes, all-black looks sleek if textures are mixed.");
        qaMap.put("What colors look expensive?", "Neutral tones like beige, camel, navy, and grey.");
        qaMap.put("What color pants with white shirt?", "Black, navy, grey, beige, or olive pants work perfectly.");
        qaMap.put("Can I mix blue and black?", "Yes, especially dark blue with black for a modern look.");
        qaMap.put("What color shoes with blue jeans?", "White, brown, tan, or black depending on the vibe.");
        qaMap.put("What colors clash together?", "Neon shades with muted tones usually clash.");
        qaMap.put("Is grey a safe color?", "Yes, grey is extremely versatile and neutral.");
        qaMap.put("What color kurta suits everyone?", "White, cream, and light blue suit most people.");
        qaMap.put("What color dress for night events?", "Black, wine, emerald, or navy work best at night.");
        qaMap.put("Can I wear pastel in winter?", "Yes, pair pastels with darker layers.");
        qaMap.put("What color jeans are most versatile?", "Dark blue and black jeans are most versatile.");
        qaMap.put("What colors make you look taller?", "Monochrome and darker shades elongate your look.");
        qaMap.put("What colors suit fair skin?", "Pastels, navy, emerald, and soft pink look great.");
        qaMap.put("What colors suit medium skin tone?", "Olive, mustard, navy, maroon, and white work well.");
        qaMap.put("What colors suit dusky skin?", "Bright colors like yellow, teal, red, and white shine.");
        qaMap.put("Should fair skin avoid yellow?", "Very pale yellow may wash out fair skin.");
        qaMap.put("Does black suit all skin tones?", "Yes, black flatters almost everyone.");
        qaMap.put("What lipstick color suits dusky skin?", "Berry, wine, brown, and red shades look great.");
        qaMap.put("What colors brighten dark skin?", "White, cobalt blue, orange, and neon shades.");
        qaMap.put("Is beige good for dusky skin?", "Yes, when paired with contrast accessories.");
        qaMap.put("What colors suit warm undertone?", "Earthy tones like mustard, olive, and rust.");
        qaMap.put("What colors suit cool undertone?", "Blues, purples, emerald, and grey.");
        qaMap.put("How to know my undertone?", "Check vein color or jewelry preference.");
        qaMap.put("Can fair skin wear black?", "Yes, black creates a sharp contrast on fair skin.");
        qaMap.put("Best kurta color for dusky skin?", "Royal blue, maroon, and off-white.");
        qaMap.put("Best saree color for fair skin?", "Red, pastel pink, and emerald green.");
        qaMap.put("What colors to avoid for dark skin?", "Very dull browns close to skin tone.");
        qaMap.put("What to wear to college daily?", "Comfortable jeans, T-shirt, and sneakers.");
        qaMap.put("What to wear to a wedding?", "Ethnic wear or formal traditional outfits.");
        qaMap.put("What to wear on a date?", "Something comfortable but polished like a fitted top and jeans.");
        qaMap.put("What to wear to office?", "Formal shirts, trousers, or smart casuals.");
        qaMap.put("What to wear to a party?", "Statement top, fitted bottoms, and accessories.");
        qaMap.put("What to wear to an interview?", "Neutral formal attire with minimal accessories.");
        qaMap.put("What to wear to a funeral?", "Muted colors like black, grey, or navy.");
        qaMap.put("What to wear on festivals?", "Bright ethnic outfits or traditional wear.");
        qaMap.put("What to wear to a night out?", "Dark colors with bold accessories.");
        qaMap.put("What to wear to brunch?", "Light colors, flowy tops, and minimal makeup.");
        qaMap.put("What to wear on first day?", "Simple, neat, and confident outfit.");
        qaMap.put("What to wear to a family function?", "Traditional or semi-ethnic outfits.");
        qaMap.put("What to wear to a meeting?", "Well-fitted formal or smart casuals.");
        qaMap.put("What to wear to travel?", "Comfortable clothes with layers.");
        qaMap.put("What to wear to beach?", "Breathable fabrics like cotton and linen.");
        qaMap.put("What suits a petite body?", "High-waisted bottoms and fitted tops.");
        qaMap.put("What suits a curvy body?", "Wrap dresses and structured outfits.");
        qaMap.put("What suits tall girls?", "Wide-leg pants and long dresses.");
        qaMap.put("What suits short height?", "Monochrome outfits and cropped jackets.");
        qaMap.put("How to look taller?", "Vertical stripes and fitted silhouettes.");
        qaMap.put("How to hide tummy?", "High-waisted bottoms and flowy tops.");
        qaMap.put("What jeans suit pear shape?", "Straight-leg or bootcut jeans.");
        qaMap.put("What jeans suit apple shape?", "High-rise straight jeans.");
        qaMap.put("What dresses suit hourglass?", "Bodycon and wrap dresses.");
        qaMap.put("What tops suit broad shoulders?", "V-neck and flowy tops.");
        qaMap.put("What shoes go with dresses?", "Heels, flats, or sneakers based on dress style.");
        qaMap.put("Can sneakers go with jeans?", "Yes, sneakers pair perfectly with jeans.");
        qaMap.put("What shoes for formal wear?", "Loafers, heels, or formal shoes.");
        qaMap.put("What accessories elevate outfit?", "Watch, belt, and minimal jewelry.");
        qaMap.put("Is belt necessary?", "Belts help define the waist and polish look.");
        qaMap.put("What bag suits casual outfits?", "Tote or sling bags work best.");
        qaMap.put("What jewelry for daily wear?", "Minimal studs or small hoops.");
        qaMap.put("Can gold and silver mix?", "Yes, when done subtly.");
        qaMap.put("What shoes for ethnic wear?", "Juttis, mojris, or sandals.");
        qaMap.put("Are watches important?", "Watches add structure and style.");
        qaMap.put("What to wear in summer?", "Light fabrics like cotton and linen.");
        qaMap.put("What to wear in winter?", "Layers, jackets, and warm fabrics.");
        qaMap.put("What colors for summer?", "White, pastels, and light shades.");
        qaMap.put("What colors for winter?", "Dark shades like maroon and navy.");
        qaMap.put("What are wardrobe basics?", "White tee, jeans, black jacket.");
        qaMap.put("How many basics should I own?", "At least 10–15 versatile pieces.");
        qaMap.put("Can basics look stylish?", "Yes, with good fit and layering.");
        qaMap.put("How to repeat outfits?", "Change accessories or layers.");
        qaMap.put("Is denim always in style?", "Yes, denim never goes out of fashion.");
        qaMap.put("What fabric is most comfortable?", "Cotton is best for daily wear.");
        qaMap.put("What color shirt goes with black jeans?",
                "Black jeans are versatile—white, grey, beige, or pastel shirts work really well.");

        qaMap.put("What color top suits blue jeans?",
                "Blue jeans pair easily with white, black, pastel, or earthy tones like brown and olive.");

        qaMap.put("Can I wear white shirt with beige pants?",
                "Yes, white and beige look clean, elegant, and very balanced together.");

        qaMap.put("What colors go with grey trousers?",
                "Grey trousers go well with black, white, navy blue, and soft pastel shades.");

        qaMap.put("Which color jeans match a pastel top?",
                "Light blue, white, or beige jeans complement pastel tops best.");

        qaMap.put("What color pants with a black top?",
                "Black tops look great with blue jeans, beige pants, grey trousers, or white pants.");

        qaMap.put("Can brown and black be worn together?",
                "Yes, when styled properly, brown and black can look very classy and modern.");

        qaMap.put("What colors go with olive green?",
                "Olive green pairs well with white, beige, black, brown, and cream shades.");

        qaMap.put("What color goes best with navy blue?",
                "White, beige, grey, and light pink complement navy blue beautifully.");

        qaMap.put("Can I wear white on white?",
                "Yes, white-on-white looks elegant—just add contrast with accessories.");

        qaMap.put("What shoes go with white pants?",
                "Brown, tan, or white sneakers work best for a clean casual look.");

        qaMap.put("Which shoes suit black jeans?",
                "Black jeans go well with sneakers, boots, loafers, or even formal shoes.");

        qaMap.put("Can sneakers be worn with dresses?",
                "Yes, sneakers with dresses create a comfortable and trendy casual style.");

        qaMap.put("What footwear is best for office wear?",
                "Loafers, formal shoes, block heels, or clean flats are ideal for office wear.");

        qaMap.put("Are loafers good for casual outfits?",
                "Yes, loafers are stylish and comfortable for smart-casual looks.");

        qaMap.put("What shoes go with ethnic wear?",
                "Ethnic outfits pair well with juttis, sandals, or traditional flats.");

        qaMap.put("Can heels be worn with jeans?",
                "Yes, heels with jeans elevate the outfit and make it look more polished.");

        qaMap.put("What color shoes with pastel outfits?",
                "Neutral shades like white, beige, nude, or soft brown work best.");

        qaMap.put("What colors suit fair skin tone?",
                "Pastels, soft pinks, blues, and light neutrals usually suit fair skin well.");

        qaMap.put("What colors suit dusky skin?",
                "Bright colors, jewel tones, and warm shades look stunning on dusky skin.");

        qaMap.put("What colors look good on wheatish skin?",
                "Earthy tones, warm pastels, and rich colors complement wheatish skin nicely.");

        qaMap.put("Should dark skin avoid bright colors?",
                "Not at all—bright and bold colors actually enhance dark skin tones.");

        qaMap.put("What lipstick color suits my skin tone?",

                "Choose nude or pink for fair skin, warm browns for wheatish, and bold shades for dusky skin.");

        qaMap.put("What should I wear to a wedding?",
                "Ethnic wear, elegant dresses, or festive outfits are ideal for weddings.");

        qaMap.put("What outfit is good for a party?",
                "Party outfits can include dresses, stylish tops with jeans, or statement pieces.");

        qaMap.put("What should I wear to office?",
                "Go for clean, fitted outfits like shirts, trousers, kurtis, or formal dresses.");

        qaMap.put("What to wear on a first date?",
                "Wear something comfortable and confident—smart casual outfits work best.");

        qaMap.put("What should I wear for college daily?",
                "Comfortable basics like jeans, tops, kurtis, or casual dresses are perfect.");

        qaMap.put("What outfit suits a formal event?",
                "Structured outfits like blazers, formal dresses, or tailored suits work well.");

        qaMap.put("What clothes suit pear-shaped body?",
                "A-line dresses, high-waist bottoms, and balanced tops suit pear-shaped bodies.");

        qaMap.put("What clothes make me look taller?",
                "High-waist bottoms, monochrome outfits, and vertical stripes help elongate height.");

        qaMap.put("What outfits hide belly fat?",
                "Flowy tops, high-waist bottoms, and layered outfits help conceal the midsection.");

        qaMap.put("What to wear in summer?",
                "Light fabrics like cotton and linen in breathable, light colors are best.");

        qaMap.put("What outfits suit winter?",
                "Layered outfits with sweaters, coats, and boots work well in winter.");

        qaMap.put("What accessories suit simple outfits?",
                "Minimal jewelry, a nice bag, or statement footwear can elevate simple outfits.");

        qaMap.put("How to match belt with shoes?",
                "Matching belt color with shoe color creates a clean and polished look.");

        qaMap.put("What bag suits casual outfits?",
                "Tote bags, sling bags, or backpacks work well for casual outfits.");

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
}
