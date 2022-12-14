package com.artbird.onsite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.artbir.ClientDetailsScreen
import com.artbird.onsite.domain.*
import com.artbird.onsite.ui.address.AddressViewModel
import com.artbird.onsite.ui.appointment.AppointmentViewModel
import com.artbird.onsite.ui.auth.AuthViewModel
import com.artbird.onsite.ui.auth.LoginScreen
import com.artbird.onsite.ui.building.*
import com.artbird.onsite.ui.quote.QuoteDetailsScreen
import com.artbird.onsite.ui.quote.QuoteViewModel
import com.artbird.onsite.ui.role.RoleViewModel
import com.artbird.onsite.ui.window.WindowListScreen
import com.artbird.onsite.ui.window.WindowViewModel
import java.io.File

import androidx.compose.runtime.remember
import com.artbird.onsite.ui.auth.SignupScreen
import com.artbird.onsite.ui.project.ProjectDetailsScreen
import com.artbird.onsite.ui.project.ProjectViewModel
import com.artbird.onsite.ui.settings.SettingsScreen
import com.artbird.onsite.ui.account.AccountViewModel
import com.artbird.onsite.ui.address.AddressAutocompleteScreen
import com.artbird.onsite.ui.appointment.AppointmentDetailsScreen
import com.artbird.onsite.ui.appointment.AppointmentFormScreen
import com.artbird.onsite.ui.appointment.AppointmentListScreen
import com.artbird.onsite.ui.client.*
import com.artbird.onsite.ui.project.ProjectFormScreen
import com.artbird.onsite.ui.project.ProjectListScreen
import com.artbird.onsite.ui.settings.ChangePasswordScreen
import com.artbird.onsite.ui.window.WindowFormScreen

data class MenuItem(val label: String, val path : String, val icon: ImageVector)

@Composable
fun NaviRoute(
    user: Account,
    address: String,
    authViewModel: AuthViewModel,
    accountViewModel: AccountViewModel,
    navController: NavController,
    roleViewModel: RoleViewModel,
    appointmentViewModel: AppointmentViewModel,
    windowViewModel: WindowViewModel,
    buildingViewModel: BuildingViewModel,
    quoteViewModel: QuoteViewModel,
    addressViewModel: AddressViewModel,
    projectViewModel: ProjectViewModel,
    profileViewModel: ProfileViewModel,
    appointmentId: String,
    appointment: Appointment2,
    onMeasure: (appointmentId: String) -> Unit,
//    onChangeClient: (client: Profile) -> Unit,
    onChangeAppointment:(appointment: Appointment2) -> Unit,
    onChangeAddress: (address: String) -> Unit,
    dir: File?,
    startDestination: String,
//    clientProfile: Profile,
){
    val roles: List<Role> by roleViewModel.roles.observeAsState(arrayListOf())
    var highlightedClient by remember { mutableStateOf(null) }

    NavHost(
        navController = navController as NavHostController,
        startDestination = startDestination,
    ) {
        composable(route = "clients") {
            if (user != null) {
                ClientListScreen(
                    navController,
                    accountViewModel,
                    profileViewModel,
                    user,
                )
            }
        }

        composable(route = "clients/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ){
            if (user != null) {
                ClientDetailsScreen(
                    navController,
                    profileViewModel,
                    projectViewModel,
                    clientId = it.arguments?.getString("id")!!,
                )
            }
        }

        composable(route = "clients/{clientId}/projects/{projectId}",
            arguments = listOf(
                navArgument("clientId") {
                    type = NavType.StringType
                },
                navArgument("projectId") {
                    type = NavType.StringType
                },
            ),
            )
        {
            ClientProjectDetailsScreen(
                navController = navController,
                projectViewModel = projectViewModel,
                clientId = it.arguments?.getString("clientId")!!,
                projectId = it.arguments?.getString("projectId")!!,
            )
        }

        composable(route = "clients/{id}/form",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ){
            if (user != null) {
                val role = roles!!.find { it.name == "client"}
                ClientFormScreen(
                    navController,
                    accountViewModel,
                    profileViewModel,
                    clientId = it.arguments?.getString("id")!!,
                    recommender = user,
                    role = role!!
                )
            }
        }

        composable(route = "search/client"){
            ClientSearchScreen(
                navController = navController,
                accountViewModel = accountViewModel,
                profileViewModel = profileViewModel,
                roles=roles,
                user=user,
            )
        }

        composable(route = "appointments") {
            if (user != null) {
                AppointmentListScreen(
                    navController=navController,
                    appointmentViewModel=appointmentViewModel,
                    employee = user,
                    onSelectAppointment = onChangeAppointment
//                    onMeasure,
//                    onAdd={
//                        onChangeClient(
//                            Client2(
//                                account = Account2(),
//                                address = Address(),
//                                recommender = BaseAccount(),
//                            )
//                        )
//                        onChangeAppointment(
//                            Appointment(
//                                _id = "",
//                                title="",
//                                notes="",
//                                start = "",
//                                end = "",
//                                type = "",
//                                client = BaseClient(client.id, BaseAccount(client.account.id, client.account.username)),
//                                employee = BaseAccount(user.id, user.username),
//                                createBy = BaseAccount(user.id, user.username),
//                            )
//                        )
//                    }
                )
            }
        }

        composable(route = "appointments/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        )
        {
            if (user != null) {
                AppointmentDetailsScreen(
                    navController = navController,
                    appointmentId = it.arguments?.getString("id")!!,
                    appointmentViewModel = appointmentViewModel,
                    onSelectAppointment = onChangeAppointment,
                )
            }
        }

        composable(route = "appointments/{id}/form",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        )
        {
            if (user != null) {
                AppointmentFormScreen(
                    navController = navController,
                    appointmentId = it.arguments?.getString("id"),
                    appointmentViewModel = appointmentViewModel,
                    profileViewModel=profileViewModel,
                    user,
                )
            }
        }

        composable(route = "buildings"){
            BuildingListScreen(
                navController,
                buildingViewModel,
                appointment
            )
        }

        composable(route = "buildings/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            ))
        {
            BuildingDetailsScreen(
                navController = navController,
                buildingViewModel = buildingViewModel,
                buildingId = it.arguments?.getString("id"),
            )
        }

        composable(route = "buildings/{id}/form",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            ))
        {
            BuildingFormScreen(
                navController,
                buildingViewModel,
                appointment._id,
                buildingId = it.arguments?.getString("id")!!,
            )
        }

        composable(route = "buildings/{buildingId}/floors/{id}",
            arguments = listOf(
                navArgument("buildingId") {
                    type = NavType.StringType
                },
                navArgument("id") {
                    type = NavType.StringType
                },
            ))
        {
            FloorDetailsScreen(
                navController,
                buildingViewModel,
                buildingId = it.arguments?.getString("buildingId")!!,
                floorId = it.arguments?.getString("id")!!,
            )
        }

        composable(route = "buildings/{buildingId}/floors/{id}/form",
            arguments = listOf(
                navArgument("buildingId") {
                    type = NavType.StringType
                },
                navArgument("id") {
                    type = NavType.StringType
                },
            ))
        {
            FloorFormScreen(
                navController,
                buildingViewModel,
                buildingId = it.arguments?.getString("buildingId"),
                floorId = it.arguments?.getString("id")!!
            )
        }

        composable(route = "buildings/{buildingId}/floors/{floorId}/rooms/{roomId}",
            arguments = listOf(
                navArgument("buildingId") {
                    type = NavType.StringType
                },
                navArgument("floorId") {
                    type = NavType.StringType
                },
                navArgument("roomId") {
                    type = NavType.StringType
                },
            )
        )
        {
            RoomDetailsScreen(
                navController,
                buildingViewModel,
                windowViewModel,
                buildingId = it.arguments?.getString("buildingId")!!,
                floorId = it.arguments?.getString("floorId")!!,
                roomId = it.arguments?.getString("roomId")!!,
            )
        }

        composable(route = "buildings/{buildingId}/floors/{floorId}/rooms/{roomId}/form",
            arguments = listOf(
                navArgument("buildingId") {
                    type = NavType.StringType
                },
                navArgument("floorId") {
                    type = NavType.StringType
                },
                navArgument("roomId") {
                    type = NavType.StringType
                },
            ))
        {
            RoomFormScreen(
                navController,
                buildingViewModel,
                buildingId = it.arguments?.getString("buildingId")!!,
                floorId = it.arguments?.getString("floorId")!!,
                roomId = it.arguments?.getString("roomId")!!,
            )
        }


        composable(route = "buildings/{buildingId}/floors/{floorId}/rooms/{roomId}/windows/{id}/form",
            arguments = listOf(
                navArgument("buildingId") {
                    type = NavType.StringType
                },
                navArgument("floorId") {
                    type = NavType.StringType
                },
                navArgument("roomId") {
                    type = NavType.StringType
                },
                navArgument("id") {
                    type = NavType.StringType
                },
            ))
        {
            WindowFormScreen(
                navController,
//                buildingViewModel,
                windowViewModel,
//                appointment = Appointment(),
//                buildingId = it.arguments?.getString("buildingId")!!,
//                floorId = it.arguments?.getString("floorId")!!,
                roomId = it.arguments?.getString("roomId")!!,
                windowId = it.arguments?.getString("id")!!,
            )
        }



//        composable(route = "buildings/{id}/floors/{index}",
//            arguments = listOf(
//                navArgument("id") {
//                    type = NavType.StringType
//                },
//                navArgument("index") {
//                    type = NavType.StringType
//                },
//            )
//        )
//        {
//            FloorDetailsView(
//                buildingId = it.arguments?.getString("id"),
//                floorIndex = it.arguments?.getString("index"),
//                navController = navController,
//                buildingViewModel = buildingViewModel,
//                windowViewModel = windowViewModel,
//            )
//        }

//        composable(route = "appointments/{id}/buildings",
//            arguments = listOf(
//                navArgument("id") {
//                    type = NavType.StringType
//                }
//            )
//        ){
//            MeasureScreen(
//                navController,
//                appointmentId=it.arguments?.getString("id"),
//                appointmentViewModel,
//                buildingViewModel,
//                windowViewModel,
//            )
//        }

        composable(route = "quotes"){
//            QuoteListScreen(
//                navController,
//                quoteViewModel,
//                appointment,
//                client
//            )
        }

        composable(route = "quotes/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ){
            if (dir != null) {
                QuoteDetailsScreen(
                    dir,
                    navController,
                    it.arguments?.getString("id"),
                    quoteViewModel,
                )
            }
        }

        composable(route = "appointments/{id}/quote",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ){
            if (dir != null) {
                QuoteDetailsScreen(
                    dir,
        //                context,
                    navController,
                    it.arguments?.getString("id"),
                    quoteViewModel
                )
            }
        }

        composable(route = "rooms/{id}/windows",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ){
            WindowListScreen(
                navController,
                it.arguments?.getString("id"),
                windowViewModel
            )
        }

        composable(route = "settings") {
                SettingsScreen(navController)
        }

        composable(route = "change-password") {
            ChangePasswordScreen(
                navController,
                authViewModel,
                user
            )
        }

        composable(route = "projects") {
            ProjectListScreen(navController, projectViewModel, user.id)
        }



        composable(route = "projects/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ){
            ProjectDetailsScreen(
                navController,
                projectViewModel,
                it.arguments?.getString("id")!!,
            )
        }

        composable(route = "projects/{id}/form",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        )
        {
            ProjectFormScreen(
                navController = navController,
                profileViewModel = profileViewModel,
                it.arguments?.getString("id")!!,
                address,
            )
        }

        composable(route = "address/autocomplete") {
            AddressAutocompleteScreen(
                navController,
                addressViewModel,
                onSelect = onChangeAddress
            )
        }
//        composable(
//            route = "windows/{id}",
//            arguments = listOf(
//                navArgument("id") {
//                    type = NavType.StringType
//                }
//            )
//        ) {
//            WindowScreen(
//                navController,
//                it.arguments?.getString("id"),
//                windowViewModel,
//            )
//        }
//        composable(
//            route = "dashboard",
//        ) {
//            DashboardScreen(
//                navController,
//                clientViewModel
//            )
//        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(
    accountViewModel: AccountViewModel,
    authViewModel: AuthViewModel,
    addressViewModel: AddressViewModel,
    roleViewModel: RoleViewModel,
    clientViewModel: ProfileViewModel,
    appointmentViewModel: AppointmentViewModel,
    windowViewModel: WindowViewModel,
    buildingViewModel: BuildingViewModel,
    quoteViewModel: QuoteViewModel,
    projectViewModel: ProjectViewModel,
    dir: File,
){
    val navController = rememberNavController()
    var isLoggedIn by remember { mutableStateOf(false) }
    var page by remember { mutableStateOf("login")}
    var appointmentId by remember { mutableStateOf("") }
    var user: Account? by remember { mutableStateOf(null) } // logged in user
    var address by remember { mutableStateOf("") }

//    var clientProfile by remember { mutableStateOf(Profile())}

    var appointment by remember { mutableStateOf(
        Appointment2()
    )}


    fun handleSelectAppointment(id: String){
        appointmentId = id
    }



    if(isLoggedIn) {
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        roleName = user!!.role.name,
                        onClick = {it -> navController.navigate(it.path)}
                    ) },
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                bottom = it.calculateBottomPadding()
                            )
                    ) {
                        NaviRoute(
                            user = user!!,
                            address,
                            navController = navController,
                            authViewModel = authViewModel,
                            accountViewModel = accountViewModel,
                            roleViewModel = roleViewModel,
                            appointmentViewModel = appointmentViewModel,
                            windowViewModel = windowViewModel,
                            buildingViewModel = buildingViewModel,
                            quoteViewModel = quoteViewModel,
                            addressViewModel = addressViewModel,
                            projectViewModel = projectViewModel,
                            profileViewModel = clientViewModel,
                            appointmentId = appointmentId,
                            appointment = appointment,
                            onMeasure = ::handleSelectAppointment, //fix me
//                            onChangeClient = { it ->
//                                clientProfile = it
//                            },
                            onChangeAppointment = { it ->
                                appointment = it
                            },
                            onChangeAddress = {
                              address = it
                            },
                            dir = dir, // LocalContext.current.filesDir,
                            startDestination = "clients",
                        )

                    }
                },

// ------- Use in future ---------
//        floatingActionButton = {
//            if(mode !="edit") {
//                FloatingActionButton(
//                    onClick = {},
//                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
//                ) {
//                    Icon(Icons.Filled.Add, "Add Building")
//                }
//            }
//        }

            )
    }else{
        if(page == "login"){
            LoginScreen(
                authViewModel = authViewModel,
                onSubmit = {
                    isLoggedIn = it.jwt.isNotEmpty()
                    user = it.account
                    roleViewModel.getRoles()
                },
                onPageChange = { page = it }
            )
        }else{
            SignupScreen(
                authViewModel,
                roleViewModel,
                onSubmit = {
                    isLoggedIn = it.jwt.isNotEmpty()
                    user = it.account;
                    roleViewModel.getRoles()
                },
                onPageChange = {page = it}
            )
        }
    }
}