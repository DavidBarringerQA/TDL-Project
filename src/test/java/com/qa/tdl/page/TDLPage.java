package com.qa.tdl.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class TDLPage{

	public final String URL = "http://localhost:8080/imp/live/index.html/";

	@FindBy(id="addButton")
	private WebElement addButton;

	@FindBy(id="description")
	private WebElement descriptionInput;

	@FindBy(xpath="//*[@id=\"priority\"]")
	private WebElement priorityInput;

	@FindBy(id="time")
	private WebElement timeInput;

	@FindBy(id="submit")
	private WebElement submitButton;

	@FindBy(id="list")
	private WebElement list;

	@FindBy(xpath="//*[@id=\"list\"]/tr/td[6]/button[1]")
	private WebElement editButton;

	@FindBy(xpath="//*[@id=\"list\"]/tr/td[6]/button[2]")
	private WebElement deleteButton;

	@FindBy(xpath="//*[@id=\"list\"]/tr/td[6]/button")
	private WebElement confirmButton;

	@FindBy(id="edited-description-1")
	private WebElement descriptionEdited;

	@FindBy(id="edited-priority-1")
	private WebElement priorityEdited;

	@FindBy(id="edited-completed-1")
	private WebElement completedEdited;

	public void addItem(String desc, int prio, String date, String time){
		addButton.click();
		descriptionInput.sendKeys(desc);
		Select priorityOptions = new Select(priorityInput);
		priorityOptions.selectByIndex(prio);
		timeInput.sendKeys(date + Keys.ARROW_RIGHT + time);
		submitButton.click();
	}

	public String getFromList(int row, int col){
		String path = "//*[@id=\"list\"]/tr[" + row + "]/td[" + col + "]";
		WebElement target = list.findElement(By.xpath(path));
		return target.getText();
	}

	public void editItem(String desc, int prio, int completed){
		editButton.click();
		descriptionEdited.clear();
		descriptionEdited.sendKeys(desc);
		Select priorityOptions = new Select(priorityEdited);
		priorityOptions.selectByIndex(prio);
		Select completedOptions = new Select(completedEdited);
		completedOptions.selectByIndex(completed);
		confirmButton.click();
	}

	public void deleteItem(){
		deleteButton.click();
	}
}
