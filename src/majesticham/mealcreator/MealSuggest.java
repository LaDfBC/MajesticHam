package majesticham.mealcreator;

import java.util.ArrayList;

public class MealSuggest {
	private ArrayList<String> ingredients;
	private ArrayList<Meal> all_meals; // Until we query the database, this gets the point across
	
	public MealSuggest(ArrayList<String> source_ingredients)
	{
		ingredients = new ArrayList<String>();
		ingredients = source_ingredients;
	}
	
	public ArrayList<Meal> suggest_meals()
	{
		ArrayList<Meal> matching_meals;
		matching_meals = new ArrayList<Meal>(); 
		/*	
	    int meals_per_page = 10;
		int i = 0;
		while (matching_meals.size() < meals_per_page)
		{
			for (long j = 0; j < ingredients.size(); j++)
			{
				for (long k = 0; k < all_meals.get(i).ingredients.size(); k++)
				{
					if (ingredients[j] == all_meals[i].ingredients[k])
					{
						continue;
					}
					
				}
			}
		}
		*/
		return matching_meals;
	}
}
