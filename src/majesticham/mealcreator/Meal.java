package majesticham.mealcreator;

import java.util.ArrayList;

import android.widget.Toast;

public class Meal 
{
	private ArrayList<String> ingredients;
	private int num_ingredients;
	private String instructions;
	private ArrayList<String> categories;
	private int calories;
	
	public Meal()
	{
		ingredients = new ArrayList<String>();
		instructions = "Default_Meal_Ignore";
		categories = new ArrayList<String>();
		categories.add("None");
		calories = 0;
	}
	
	public Meal(ArrayList<String> source_ingredients, String source_instructions, 
			    ArrayList<String> source_categories, int source_calories)
	{
		instructions = source_instructions;
		calories = source_calories; // INCLUDE ERROR CHECKING...Java is kind of gross.
		
		ingredients = new ArrayList<String>();
		ingredients = source_ingredients;
		
		categories = new ArrayList<String>();
		categories = source_categories;
	}
	
	public boolean ingredient_search(String query_ingredient)
	{
		return ingredients.contains(query_ingredient);
	}
	
	public void print_instructions()
	{
//		Toast toast = Toast.makeText(MajesticHamActivity.this, instructions, Toast.LENGTH_SHORT);
//		toast.show();
	}
	
	public void output_meal()
	{
		
	}
	
	public void input_meal()
	{
		
	}
	
	public void set_categories(ArrayList<String> source_categories)
	{
		categories = source_categories;
	}
	
	public int get_calorie_count()
	{
		return calories;
	}
}
