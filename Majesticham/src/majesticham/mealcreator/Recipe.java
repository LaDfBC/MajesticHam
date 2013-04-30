package majesticham.mealcreator;

import java.util.List;

public class Recipe {
	
	private String directions;
	private String footnote;
	private String prep;
	private String name;
	private List<String> ingredients;
	
	public Recipe(String directions, String footnote, String prep, String name, List<String> ingredients)
	{
		this.directions = directions;
		this.footnote = footnote;
		this.prep = prep;
		this.name = name;
		this.ingredients = ingredients;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public String getFootnote() {
		return footnote;
	}

	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

	public String getPrep() {
		return prep;
	}

	public void setPrep(String prep) {
		this.prep = prep;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	
}
