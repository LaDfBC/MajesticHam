package majesticham.mealcreator;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient {
	//IF YOU ADD MORE ENCODE THE JSON AT BOTTOM
	private String name;
	private int quantity;
	private int goodLength;
	private String unit; //ENUM????
	private Date addDate;
	
	public Ingredient(String name, int quantity, int goodLength, String unit, Date addDate){
		this.name = name;
		this.quantity = quantity;
		this.goodLength = goodLength;
		this.unit = unit;
		this.addDate = addDate;
	}
	public Ingredient(String name, int quantity, String unit){
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
	}
	public Ingredient(String name, int quantity){
		this.name = name;
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getGoodLength() {
		return goodLength;
	}
	public void setGoodLength(int goodLength) {
		this.goodLength = goodLength;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String toJSON(){
		String retStr;	 
		retStr = "{" +
				 	"\"name\": \"" + name + "\"," +
				 	"\"quantity\": \"" + quantity + "\"," +
				 	"\"goodLength\": \"" + goodLength + "\"," +
				 	"\"unit\": \"" + unit + "\"," +
				 	"\"addDate\": \"" + addDate.toString() + "\"" +
				 "}";
		return retStr;
	}
}
