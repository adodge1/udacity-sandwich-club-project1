package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String INITIAL_NODE = "name";
    private static final String ITEM_NAME = "mainName";
    //this one is an array on the json
    private static final String ITEM_AKA = "alsoKnownAs";
    private static final String ITEM_ORIGIN = "placeOfOrigin";
    private static final String ITEM_DESCRIPTION = "description";
    private static final String ITEM_IMAGE_SRC = "image";
    //this one is an array on the json
    private static final String ITEM_INGREDIENTS = "ingredients";

    /**
     * What it does: This method parses the item from strings.xml and it is used by DetailActivity.java
     * What it returns: Sandwich obj with all the info of the sandwich (name, mainName,alsoKnownAs,placeOfOrigin,description,image src and ingredients
     * @param json
     * @return Sandwich obj
     * @throws JSONException
     */

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        Sandwich sandwich = new Sandwich();
        JSONObject sandwichJson = new JSONObject(json);

        JSONObject name = sandwichJson.getJSONObject(INITIAL_NODE);
        //this node is a string under name node
        String mainName = name.getString(ITEM_NAME);
        sandwich.setMainName(mainName);

        //this node is an array
        JSONArray knownAsArray = name.getJSONArray(ITEM_AKA);
        List<String> alsoKnownAs = new ArrayList<>();

        for(int i= 0 ; i< knownAsArray.length() ; i++){
            if(knownAsArray.getString(i)!="" || !knownAsArray.getString(i).equals("")){
                alsoKnownAs.add(knownAsArray.getString(i));
            }
        }
        sandwich.setAlsoKnownAs(alsoKnownAs);

        //this node is a string under name node
        String placeOfOrigin = sandwichJson.getString(ITEM_ORIGIN);

        sandwich.setPlaceOfOrigin(placeOfOrigin);
        //this node is a string under name node
        String description = sandwichJson.getString(ITEM_DESCRIPTION);
        sandwich.setDescription(description);
        //this node is a string under name node
        String imgSrc = sandwichJson.getString(ITEM_IMAGE_SRC);
       // Log.d(imgSrc, " parseSandwichJson()");
        //if link to File does not exist, gives a 404 error!
        if(mainName.equals("Shawarma")){
            imgSrc ="http://www.shawarmaholic.com/data1/images/13.jpg";
        }
         sandwich.setImage(imgSrc);

        //this node is an array
        JSONArray ingredientsArray = sandwichJson.getJSONArray(ITEM_INGREDIENTS);
        List<String> ingredientsList = new ArrayList<>();

        for(int i=0 ; i< ingredientsArray.length() ; i++){
            if(ingredientsArray.getString(i)!="" || !ingredientsArray.getString(i).equals("")) {
                ingredientsList.add(ingredientsArray.getString(i));
            }
        }
        sandwich.setIngredients(ingredientsList);

        return sandwich;
    }
}
