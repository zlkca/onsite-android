@file:OptIn(ExperimentalMaterial3Api::class)

package com.artbird.onsite.ui.client

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.artbird.onsite.domain.*
import com.artbird.onsite.ui.account.AccountViewModel
import com.artbird.onsite.ui.components.*

@Composable
fun AccountListItem(item: Account, selected: Boolean, index:Int){
    val colorScheme = MaterialTheme.colorScheme
    Column(){
        Title2(
            text = item.username,
            color = if (selected) colorScheme.onPrimary else colorScheme.onBackground
        )

        Body3(
            text = item.email,
            color = if (selected) colorScheme.onPrimary else colorScheme.onBackground,
        )

        Body3(
            text = item.phone,
            color = if (selected) colorScheme.onPrimary else colorScheme.onBackground,
        )
    }
}

@Composable
fun AccountSearchScreen(
    navController: NavController,
    accountViewModel: AccountViewModel,
//    appointmentId: String,
    user: Account,
//    onSelect: (d: Account2) -> Unit = {},
){
    val accounts: List<Account> by accountViewModel.accounts.observeAsState(arrayListOf())
//    val clientDetails: Account2 by accountViewModel.clientDetails.observeAsState(Account2())
    var keyword by remember { mutableStateOf("") }
//    var needRedirect by remember { mutableStateOf(false) }
    var client by remember { mutableStateOf(Account()) }
    var selectedIndex by remember { mutableStateOf(0) }

//    LaunchedEffect(key1 = keyword) {
//        if (keyword != null && keyword.isNotEmpty()) {
//            accountViewModel.searchByRecommender(user.id, keyword)
//        }
//    }

//    LaunchedEffect(key1 = client) {
//        if (client != null && client.id != "") {
//            accountViewModel.getAccountDetails(client.id)
//        }
//    }

//    LaunchedEffect(key1 = clientDetails) {
//        if (clientDetails != null && clientDetails!!.id != "") {
//            keyword = clientDetails.account.username
////            onSelect(clientDetails!!)
////            if(needRedirect){
////                navController.navigate("appointments/$appointmentId/form")
////            }
//        }
//    }

    Column(modifier = Modifier.padding(8.dp)) {
        SearchList<Account>(
            keyword,
            "Find Account",
            accounts,
            onSearch = { it ->
                keyword = it
                if(keyword.length >= 3) {
                    if(user.role.name === "partner"){
//                        accountViewModel.search(user.id, keyword)
                    }else if(user.role.name === "sales"){

                    }
                }
            },
            onSelect = { index ->
                val client = accounts[index] // update client by useEffect
//              accountViewModel.getAccountDetails(client.id)
                navController.popBackStack() // .navigate("projects/new/form")
            },
            onBack = {
                navController.popBackStack() // .navigate("projects/new/form")
            },
            onClear = {
                keyword = ""
            },
            itemContent = { it, selected, index -> AccountListItem(it, selected, index) },
        )
    }
}
