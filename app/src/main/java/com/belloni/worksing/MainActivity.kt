package com.belloni.worksing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.belloni.worksing.data.model.User
import com.belloni.worksing.ui.theme.WorkSignRed
import com.belloni.worksing.ui.theme.WorkSignTheme
import com.belloni.worksing.ui.theme.WorkSignYellow
import com.belloni.worksing.ui.viewmodel.MainViewModel
import com.belloni.worksing.ui.viewmodel.UserUiState

const val API_BASE_URL = "http://192.168.0.206:4000/"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkSignTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "operator_selection") {
        composable("operator_selection") {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                OperatorSelectionScreen(navController = navController)
            }
        }
        composable(
            route = "pin_login/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
             Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                PinLoginScreen(
                    navController = navController,
                    userId = backStackEntry.arguments?.getString("userId")
                )
            }
        }
    }
}

@Composable
fun OperatorSelectionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 80.dp), // Large horizontal padding for tablet
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.worksign_x_bellonihorizontal),
            contentDescription = "WorkSign Logo",
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(vertical = 100.dp) // Large vertical padding for tablet
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            when (val state = uiState) {
                is UserUiState.Loading -> CircularProgressIndicator()
                is UserUiState.Error -> Text(text = state.message, color = MaterialTheme.colorScheme.error)
                is UserUiState.Success -> UserList(users = state.users, navController = navController)
            }
        }
    }
}

@Composable
fun UserList(users: List<User>, navController: NavController, modifier: Modifier = Modifier) {
    val notificationColors = listOf(WorkSignRed, WorkSignYellow)
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(40.dp), // Large space between cards
        contentPadding = PaddingValues(bottom = 40.dp)
    ) {
        itemsIndexed(users) { index, user ->
            UserCard(
                user = user,
                notificationColor = notificationColors[index % notificationColors.size]
            ) {
                navController.navigate("pin_login/${user.id}")
            }
        }
    }
}

@Composable
fun UserCard(user: User, notificationColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier.padding(40.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = "$API_BASE_URL${user.avatarUrl}",
                contentDescription = "Avatar of ${user.name}",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(40.dp))
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                Text(
                    text = user.name.split(" ").first(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${(1..5).random()} Tareas nuevas", // Placeholder
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = { /* TODO: Notification click action */ },
                modifier = Modifier.size(60.dp) // CORRECT: Size applied to the button
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notification Bell",
                    tint = notificationColor,
                    modifier = Modifier.fillMaxSize() // CORRECT: Icon fills the button
                )
            }
        }
    }
}


@Composable
fun PinLoginScreen(navController: NavController, userId: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Ingresar PIN para Usuario ID: $userId",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}