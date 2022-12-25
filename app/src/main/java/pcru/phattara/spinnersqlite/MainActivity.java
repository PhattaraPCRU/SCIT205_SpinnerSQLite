package pcru.phattara.spinnersqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DataBaseHelper db;
    private Spinner spinnerCategories;
    private EditText editTextNewCategory;
    private Button buttonCategory;
    private List<String> listCategories = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(this);
        spinnerCategories = findViewById(R.id.spinner_categories);
        editTextNewCategory = findViewById(R.id.editText_newCategory);
        buttonCategory = findViewById(R.id.button_addCategory);

        buttonCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCategory = editTextNewCategory.getText().toString();
                if (newCategory.equalsIgnoreCase("")){
                    editTextNewCategory.setError("Please enter a category");
                }else {
                    db.addCategory(new Category(newCategory));
                    prepareData();
                    editTextNewCategory.setText("");
                    Toast.makeText(MainActivity.this, "New Category was Successfully added to Database", Toast.LENGTH_SHORT).show();
                }
            }
        });
        prepareData();
    }
    private void prepareData() {
        listCategories.clear();
        listCategories.addAll(db.getAllCategories());
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1, listCategories);
        spinnerCategories.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}