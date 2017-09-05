package com.example.bartvankeersop.androidlistexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Vars
    static ArrayList<String> cars;
    ArrayAdapter<String> adapter;
    boolean isCarSelected;
    int selectedCarIndex;

    //UI elements
    EditText editText;
    Button btnAddCar;
    Button btnEditCar;
    Button btnRemoveCar;
    Button btnActivity;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
    }

    /**
     * Initializes variables, prepares the app.
     */
    private void setup(){
        isCarSelected = false;
        cars = new ArrayList<>();
        getCars();
        initList();
        linkUIElements();
        addListeners();
    }

    /**
     * Finds and links UI elements to a variable.
     */
    public void linkUIElements(){
        editText = (EditText) findViewById(R.id.tbName);
        btnAddCar = (Button) findViewById(R.id.btnAdd);
        btnEditCar = (Button) findViewById(R.id.btnEdit);
        btnRemoveCar = (Button) findViewById(R.id.btnRemove);
        btnActivity = (Button) findViewById(R.id.btnActivity);
    }
    /**
     * Adds cars to the list
     */
    public static void getCars(){
        cars.add("BMW");
        cars.add("Volvo");
        cars.add("Renault");
    }

    /**
     * Initializes list, configures the cars to the array adapter.
     */
    public void initList(){
        listView = (ListView) findViewById(R.id.lvCars);
        adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cars);
        listView.setAdapter(adapter);
    }

    /**
     * Adds listeners to all the buttons, and to the listview.
     */
    public void addListeners(){

        //btnAddCar method
        //Adds the car that's specified in editText to the list.
        btnAddCar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                String carName = editText.getText().toString();
                cars.add(carName);
                listView.setAdapter(adapter);
            }
        });

        //btnEditCar method
        //Edits the name of the selected car (if a car is selected).
        //If no car is selected prompts user to select a car.
        btnEditCar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                if (isCarSelected)
                {
                    cars.set(selectedCarIndex, editText.getText().toString());
                    listView.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "Changes saved.", Toast.LENGTH_LONG).show();
                    isCarSelected = false;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please select a car.", Toast.LENGTH_LONG).show();
                }}
        });

        //ListView method
        //Selects a car when clicked on.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCar = cars.get(i);
                selectedCarIndex = i;
                Toast.makeText
                        (getApplicationContext(),
                                "You selected: " + selectedCar + ". \n Press EDIT to save changes or DELETE to remove.",
                                Toast.LENGTH_LONG).show();
                editText.setText("");
                editText.append(selectedCar);
                isCarSelected = true;
            }
        });

        //btnRemoveCar method
        //Removes the selected car (if a car is selected).
        //If no car is selected prompts user to select a car.
        btnRemoveCar.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                if (isCarSelected)
                {
                    cars.remove(selectedCarIndex);
                    listView.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(), "Car removed.", Toast.LENGTH_LONG).show();
                    isCarSelected = false;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please select a car.", Toast.LENGTH_LONG).show();
                }}
        });

        //btnActivity method
        //Goes to SecondActivity and shows Toast message.
        btnActivity.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Second activity started.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
