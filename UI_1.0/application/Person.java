/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class Person {
    private final SimpleStringProperty name;
    private final SimpleStringProperty age;
    private final SimpleStringProperty malady;
    private Button action;    
    
 
     Person(String name, String age, String malady, String value) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
        this.malady = new SimpleStringProperty(malady);
        this.setAction(new Button(value));         
        
    }


	public SimpleStringProperty getName() {
		return name;
	}


	public SimpleStringProperty getAge() {
		return age;
	}


	public SimpleStringProperty getMalady() {
		return malady;
	}


	public Button getAction() {
		return action;
	}


	public void setAction(Button action) {
		this.action = action;
	}
    
}
