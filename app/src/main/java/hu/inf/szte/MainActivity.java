package hu.inf.szte;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import com.google.type.Date;
import java.util.HashMap;
import java.util.Objects;

import hu.inf.szte.databinding.ActivityMainBinding;
import hu.inf.szte.model.Ticket;
import hu.inf.szte.model.UserInfo;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_movies, R.id.nav_add_movie, R.id.nav_shows, R.id.nav_add_show, R.id.nav_mytickets, R.id.nav_profile,R.id.nav_login, R.id.nav_register)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                signout();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if (handled) {
                drawer.closeDrawer(GravityCompat.START);
            }
            return handled;
        });
        updateNavigationViewMenu();
        updateUserInfo();
    }

    public void updateNavigationViewMenu() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem loginItem = menu.findItem(R.id.nav_login);
        MenuItem registerItem = menu.findItem(R.id.nav_register);
        MenuItem logoutItem = menu.findItem(R.id.nav_logout);
        MenuItem profileItem = menu.findItem(R.id.nav_profile);
        MenuItem ticketsItem = menu.findItem(R.id.nav_mytickets);
        MenuItem addShowItem = menu.findItem(R.id.nav_add_show);
        MenuItem addMoviesItem = menu.findItem(R.id.nav_add_movie);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is logged in
            if (loginItem != null) loginItem.setVisible(false);
            if (registerItem != null) registerItem.setVisible(false);
            if (logoutItem != null) logoutItem.setVisible(true);
            if (profileItem != null) profileItem.setVisible(true);
            if (ticketsItem != null) ticketsItem.setVisible(true);

            // Check if the user is an admin
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(user.getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        boolean isAdmin = Boolean.TRUE.equals(document.getBoolean("admin"));
                        if (addShowItem != null) addShowItem.setVisible(isAdmin);
                        if (addMoviesItem != null) addMoviesItem.setVisible(isAdmin);
                    }
                }
            });
        } else {
            // User is not logged in
            if (loginItem != null) loginItem.setVisible(true);
            if (registerItem != null) registerItem.setVisible(true);
            if (logoutItem != null) logoutItem.setVisible(false);
            if (profileItem != null) profileItem.setVisible(false);
            if (ticketsItem != null) ticketsItem.setVisible(false);
            if (addShowItem != null) addShowItem.setVisible(false);
            if (addMoviesItem != null) addMoviesItem.setVisible(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void signout() {
        Log.d("TAG", "signout method called");
        FirebaseAuth.getInstance().signOut();
        // Get the NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Get the header view at position 0
        View headerView = navigationView.getHeaderView(0);
        TextView userEmailTextView = headerView.findViewById(R.id.user_email);
        TextView userNameTextView = headerView.findViewById(R.id.user_name);

        // Clear the TextViews
        userEmailTextView.setText("");
        userNameTextView.setText("");

        updateNavigationViewMenu();
        updateUserInfo(); // Add this line

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.nav_movies);
    }

    public void updateUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get the NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Get the header view at position 0
        View headerView = navigationView.getHeaderView(0);
        ImageView imageView = headerView.findViewById(R.id.imageView);

        if (user != null) {
            Task<UserInfo> userInfoTask = getUserInfo(user);
            if (userInfoTask != null) {
                userInfoTask.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        UserInfo userInfo = task.getResult();

                        // Set the TextViews
                        TextView userEmailTextView = headerView.findViewById(R.id.user_email);
                        TextView userNameTextView = headerView.findViewById(R.id.user_name);
                        userEmailTextView.setText(userInfo.email);
                        userNameTextView.setText(userInfo.name);

                        // Set the ImageView's resource to @drawable/ticket_account
                        imageView.setImageResource(R.drawable.ticket_account);
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                });
            }
        } else {
            // Set the ImageView's resource to @drawable/account_question_outline
            imageView.setImageResource(R.drawable.account_question_outline);

            // Get the header view at position 0
            TextView userEmailTextView = headerView.findViewById(R.id.user_email);
             userEmailTextView.setText("Guest");
        }
    }

    private Task<UserInfo> getUserInfo(FirebaseUser user) {
        if (user != null) {
            String userId = user.getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(userId);

            return docRef.get().continueWith(new Continuation<DocumentSnapshot, UserInfo>() {
                @Override
                public UserInfo then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String email = document.getString("email");
                            String name = document.getString("name");
                            String phone = document.getString("phone");
                            String address = document.getString("address");
                            boolean admin = Boolean.TRUE.equals(document.getBoolean("admin"));

                            ArrayList<HashMap<String, Object>> ticketsData = (ArrayList<HashMap<String, Object>>) document.get("tickets");
                            ArrayList<Ticket> tickets = new ArrayList<>();
                            if (ticketsData != null) {
                                for (HashMap<String, Object> ticketData : ticketsData) {
                                    Timestamp date = (Timestamp) ticketData.get("date");
                                    String movie = (String) ticketData.get("movie");
                                    ArrayList<Long> seats = (ArrayList<Long>) ticketData.get("seats");
                                    tickets.add(new Ticket(date, movie, seats));
                                }
                            }

                            return new UserInfo(email, name, phone, address, admin, tickets);
                        } else {
                            throw new Exception("No such document");
                        }
                    } else {
                        throw Objects.requireNonNull(task.getException());
                    }
                }
            });
        }
        return null;
    }
}
