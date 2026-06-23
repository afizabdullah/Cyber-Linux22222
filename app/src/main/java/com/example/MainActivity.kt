package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

val CyberGreen = Color(0xFF00FF41)
val CyberDark = Color(0xFF0D0D0D)
val CyberGray = Color(0xFF1A1A1A)
val CyberDarkerGray = Color(0xFF121212)
val CyberRed = Color(0xFFFF003C)
val CyberBlue = Color(0xFF00E5FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val colorScheme = darkColorScheme(
                primary = CyberGreen,
                background = CyberDark,
                surface = CyberGray,
                onPrimary = CyberDark,
                onBackground = CyberGreen,
                onSurface = CyberGreen
            )
            MaterialTheme(colorScheme = colorScheme) {
                CyberApp()
            }
        }
    }
}

@Composable
fun CyberApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController) }
        composable("terminal") { TerminalScreen(navController) }
        composable("file_manager") { FileManagerScreen(navController) }
        composable("processes") { ProcessScreen(navController) }
        composable("security") { SecurityScreen(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text("نظام سايبر لينكس - لوحة التحكم", 
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CyberDarkerGray,
                    titleContentColor = CyberGreen
                )
            )
        },
        containerColor = CyberDark
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberGray),
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "حالة النظام: متصل\nالنواة: Linux 6.1.0-android\nالبنية: aarch64\nالمستخدم: root",
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CyberGreen,
                        lineHeight = 24.sp
                    )
                }
            }
            
            val tools = listOf(
                Tool("الطرفية (Terminal)", Icons.Default.Code, "terminal"),
                Tool("مدير الملفات", Icons.Default.Folder, "file_manager"),
                Tool("مراقبة العمليات", Icons.Default.Memory, "processes"),
                Tool("لوحة الأمان", Icons.Default.Security, "security")
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(tools) { tool ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberGray),
                        modifier = Modifier
                            .height(130.dp)
                            .clickable { navController.navigate(tool.route) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = tool.icon,
                                contentDescription = tool.name,
                                tint = CyberGreen,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = tool.name,
                                color = CyberGreen,
                                fontFamily = FontFamily.Monospace,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalScreen(navController: NavHostController) {
    var command by remember { mutableStateOf("") }
    var output by remember { mutableStateOf(listOf("Cyber Linux OS Terminal v1.0", "Type 'help' for available commands.")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("الطرفية (Terminal)", fontFamily = FontFamily.Monospace) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CyberDarkerGray,
                    titleContentColor = CyberGreen,
                    navigationIconContentColor = CyberGreen
                )
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = false
            ) {
                items(output) { line ->
                    Text(
                        text = line,
                        color = CyberGreen,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp
                    )
                }
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("root@cyber:~# ", color = CyberBlue, fontFamily = FontFamily.Monospace)
                TextField(
                    value = command,
                    onValueChange = { command = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = CyberGreen,
                        unfocusedTextColor = CyberGreen,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = CyberGreen
                    ),
                    textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (command.isNotBlank()) {
                                val currentCommand = command
                                output = output + "root@cyber:~# $currentCommand"
                                
                                val response = when(currentCommand.lowercase().trim()) {
                                    "help" -> "الأوامر المتاحة: help, ls, clear, whoami, date"
                                    "ls" -> "bin  boot  dev  etc  home  lib  mnt  opt  root  run  sbin  sys  tmp  usr  var"
                                    "clear" -> {
                                        output = listOf()
                                        ""
                                    }
                                    "whoami" -> "root"
                                    "date" -> "2026-06-23"
                                    else -> "bash: $currentCommand: command not found"
                                }
                                if (response.isNotEmpty()) {
                                    output = output + response
                                }
                                command = ""
                            }
                        }
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileManagerScreen(navController: NavHostController) {
    val files = listOf(
        "etc/" to Icons.Default.Folder,
        "home/" to Icons.Default.Folder,
        "var/" to Icons.Default.Folder,
        "config.json" to Icons.Default.Description,
        "syslog.txt" to Icons.Default.Description
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("مدير الملفات", fontFamily = FontFamily.Monospace) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CyberDarkerGray,
                    titleContentColor = CyberGreen,
                    navigationIconContentColor = CyberGreen
                )
            )
        },
        containerColor = CyberDark
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(files) { (name, icon) ->
                ListItem(
                    headlineContent = { Text(name, color = CyberGreen, fontFamily = FontFamily.Monospace) },
                    leadingContent = { Icon(icon, contentDescription = null, tint = CyberBlue) },
                    colors = ListItemDefaults.colors(containerColor = CyberDark)
                )
                HorizontalDivider(color = CyberGray)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcessScreen(navController: NavHostController) {
    val processes = listOf(
        Triple("systemd", "1", "0.1%"),
        Triple("kthreadd", "2", "0.0%"),
        Triple("bash", "405", "0.5%"),
        Triple("sshd", "890", "0.2%"),
        Triple("htop", "1024", "1.2%")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("مراقبة العمليات", fontFamily = FontFamily.Monospace) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CyberDarkerGray,
                    titleContentColor = CyberGreen,
                    navigationIconContentColor = CyberGreen
                )
            )
        },
        containerColor = CyberDark
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("PROCESS", color = CyberBlue, fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f))
                Text("PID", color = CyberBlue, fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f))
                Text("CPU%", color = CyberBlue, fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f))
            }
            HorizontalDivider(color = CyberGray, modifier = Modifier.padding(bottom = 8.dp))
            processes.forEach { (name, pid, cpu) ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(name, color = CyberGreen, fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f))
                    Text(pid, color = CyberGreen, fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f))
                    Text(cpu, color = CyberGreen, fontFamily = FontFamily.Monospace, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("لوحة الأمان", fontFamily = FontFamily.Monospace) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CyberDarkerGray,
                    titleContentColor = CyberGreen,
                    navigationIconContentColor = CyberGreen
                )
            )
        },
        containerColor = CyberDark
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberGray),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("جدار الحماية (Firewall)", color = CyberBlue, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("الحالة: نشط", color = CyberGreen, fontFamily = FontFamily.Monospace)
                    Text("القواعد المطبقة: 142", color = CyberGreen, fontFamily = FontFamily.Monospace)
                }
            }
            
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberGray),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("سجل التهديدات", color = CyberRed, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("لم يتم اكتشاف تهديدات في آخر 24 ساعة.", color = CyberGreen, fontFamily = FontFamily.Monospace)
                }
            }
        }
    }
}

data class Tool(val name: String, val icon: ImageVector, val route: String)

