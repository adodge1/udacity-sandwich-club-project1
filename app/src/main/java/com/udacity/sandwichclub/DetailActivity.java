package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        //try-catch for Debugging when we call populateUI
        try {
            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Display Sandwich details
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {
        //map var from activity_detail.xml to java var
        TextView originDetail = findViewById(R.id.origin_tv);
        TextView akaDetail = findViewById(R.id.also_known_tv);
        TextView ingredientsDetail = findViewById(R.id.ingredients_tv);
        TextView descriptionDetail = findViewById(R.id.description_tv);

        //mapping the sub-titles so I can hide if no text for them and to set background color
        TextView mAKATitle =  findViewById(R.id.also_known_title_tv);
        TextView mIngredientsTitle =  findViewById(R.id.ingredients_title_tv);
        TextView mOriginTitle =  findViewById(R.id.origin_title_tv);
        TextView mDescTitle =  findViewById(R.id.description_title_tv);

        //Adding background color to the sub-titles
        mAKATitle.setBackgroundColor(Color.parseColor("#cdcdcd"));
        mIngredientsTitle.setBackgroundColor(Color.parseColor("#cdcdcd"));
        mOriginTitle.setBackgroundColor(Color.parseColor("#cdcdcd"));
        mDescTitle.setBackgroundColor(Color.parseColor("#cdcdcd"));

        //mainName
        setTitle(sandwich.getMainName());

        //Get item alsoKnownAs is an array, if not pupolated set N/A as default
        List<String> akaList = sandwich.getAlsoKnownAs();
        if(akaList.size()==0){
            mAKATitle.setVisibility(View.GONE);
            akaDetail.setVisibility(View.GONE);

        }else{
            for(int i=0 ; i< akaList.size() ; i++){
                if(i>1 && i!=akaList.size()-1){
                    akaDetail.append(akaList.get(i)+". \n");
                }else if(i==akaList.size()-1){
                    //if last in the list do not add the new line because we get an extra space
                    akaDetail.append(akaList.get(i)+".");
                }
            }

        }

        //Get Item placeOfOrigin
        String originString = sandwich.getPlaceOfOrigin();
        if(originString=="" || originString.equals("")) {
            mOriginTitle.setVisibility(View.GONE);
            originDetail.setVisibility(View.GONE);
        }else{
            originDetail.append(originString);
        }


        //Get description
        String descString = sandwich.getDescription();
        if(descString=="" || descString.equals("")) {
            mOriginTitle.setVisibility(View.GONE);
            originDetail.setVisibility(View.GONE);
        }else{
            descriptionDetail.append(descString);
        }
        //Add Item Image src
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        Picasso.with(this).load(sandwich.getImage()).into(ingredientsIv);


        //Get Ingridients
        List<String> ingredientsList = sandwich.getIngredients();
        if(ingredientsList.size()==0){
            mIngredientsTitle.setVisibility(View.GONE);
            ingredientsDetail.setVisibility(View.GONE);
        }else {
            for (int i = 0; i < ingredientsList.size(); i++) {
                if (i > 1 && i != ingredientsList.size() - 1) {
                    ingredientsDetail.append(ingredientsList.get(i) + ". \n");
                } else if (i == ingredientsList.size() - 1) {
                    //if last in the list do not add the new line because we get an extra space
                    ingredientsDetail.append(ingredientsList.get(i) + ". ");
                }
            }
        }


    }
}
