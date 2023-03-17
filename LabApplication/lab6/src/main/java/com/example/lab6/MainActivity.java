package com.example.lab6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.lab6.core.DatabaseManager;
import com.example.lab6.core.RecalculationManager;
import com.example.lab6.core.repositories.AccountsRepository;
import com.example.lab6.core.repositories.DebitsRepository;
import com.example.lab6.core.repositories.ExpensesRepository;
import com.example.lab6.core.repositories.IncomesRepository;
import com.example.lab6.core.repositories.LoansRepository;
import com.example.lab6.core.repositories.SavingsRepository;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle navigationBarToggle;
    private AccountsRepository accountsRepository;
    private SavingsRepository savingsRepository;
    private DebitsRepository debitsRepository;
    private LoansRepository loansRepository;
    private IncomesRepository incomesRepository;
    private ExpensesRepository expensesRepository;
    private RecalculationManager recalculationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationBarToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(navigationBarToggle);
        navigationBarToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        DatabaseManager db = new DatabaseManager(this);
        accountsRepository = new AccountsRepository(db);
        savingsRepository = new SavingsRepository(db);
        debitsRepository = new DebitsRepository(db);
        loansRepository = new LoansRepository(db);
        incomesRepository = new IncomesRepository(db);
        expensesRepository = new ExpensesRepository(db);
        recalculationManager = new RecalculationManager(db);
        loadMainFragment(recalculationManager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (navigationBarToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public void openMainFragment(MenuItem item) {
        hideDrawer();
        loadMainFragment(recalculationManager);
    }

    public void openAccountsFragment(MenuItem item) {
        hideDrawer();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container_view, new AccountsFragment(accountsRepository), null)
            .commit();
    }

    public void openIncomesFragment(MenuItem item) {
        hideDrawer();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container_view, new IncomesFragment(incomesRepository), null)
            .commit();
    }

    public void openExpensesFragment(MenuItem item) {
        hideDrawer();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container_view, new ExpensesFragment(expensesRepository), null)
            .commit();
    }

    public void openLoansFragment(MenuItem item) {
        hideDrawer();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container_view, new LoansFragment(loansRepository), null)
            .commit();
    }

    public void openDebitsFragment(MenuItem item) {
        hideDrawer();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container_view, new DebitsFragment(debitsRepository), null)
            .commit();
    }

    public void openSavingsFragment(MenuItem item) {
        hideDrawer();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container_view, new SavingsFragment(savingsRepository), null)
            .commit();
    }

    private void hideDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void loadMainFragment(RecalculationManager recalculationManager) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container_view, new MainFragment(recalculationManager), null)
            .commit();
    }
}