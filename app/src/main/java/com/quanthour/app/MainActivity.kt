package com.quanthour.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF10172A)
private val Blue = Color(0xFF4F7CFF)
private val Pale = Color(0xFFF4F6FC)
private val Ink = Color(0xFF18223B)
private val Muted = Color(0xFF68738A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { QuantHourApp() }
    }
}

@Composable
fun QuantHourApp() {
    var screen by remember { mutableStateOf("welcome") }
    var role by remember { mutableStateOf("Learner") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedCourse by remember { mutableStateOf("GRE") }
    var notice by remember { mutableStateOf("") }

    MaterialTheme(
        colorScheme = lightColorScheme(primary = Blue, background = Pale, surface = Color.White)
    ) {
        Surface(Modifier.fillMaxSize(), color = Pale) {
            when (screen) {
                "welcome" -> WelcomeScreen(
                    onRole = { role = it; screen = "login" },
                    onExplore = { screen = "home" }
                )
                "login" -> LoginScreen(
                    role = role, email = email, password = password,
                    onEmail = { email = it }, onPassword = { password = it },
                    onBack = { screen = "welcome" },
                    onSubmit = {
                        notice = if (email.isBlank() || password.isBlank()) "Enter your email and password to continue."
                                 else "Demo mode: connect authentication to enable secure accounts."
                        if (email.isNotBlank() && password.isNotBlank()) screen = "home"
                    },
                    onSwitch = { role = if (role == "Learner") "Creator" else "Learner" }
                )
                "home" -> HomeScreen(
                    role = role, notice = notice,
                    onCourse = { selectedCourse = it; screen = "course" },
                    onCreate = { screen = "create" },
                    onProfile = { screen = "profile" }
                )
                "course" -> CourseScreen(course = selectedCourse, onBack = { screen = "home" })
                "create" -> CreatorScreen(onBack = { screen = "home" }, onPublish = {
                    notice = "Draft saved locally for this demo. Add a backend to publish content."
                    screen = "home"
                })
                else -> ProfileScreen(role = role, onBack = { screen = "home" }, onRole = {
                    role = if (role == "Learner") "Creator" else "Learner"
                    screen = "home"
                })
            }
        }
    }
}

@Composable
fun WelcomeScreen(onRole: (String) -> Unit, onExplore: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Navy).padding(24.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            Spacer(Modifier.height(38.dp))
            Box(Modifier.size(58.dp).background(Blue, RoundedCornerShape(18.dp)), contentAlignment = Alignment.Center) {
                Text("Q", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
            }
            Spacer(Modifier.height(22.dp))
            Text("Quant Hour", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Text("Your next score starts here.", color = Color(0xFFB8C5E2), fontSize = 17.sp)
            Spacer(Modifier.height(34.dp))
            Text("PREPARE SMARTER", color = Color(0xFF8FA8FF), fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Spacer(Modifier.height(10.dp))
            Text("Build confidence in every question.", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))
            Text("Focused lessons, practice questions and progress tools for GAT, SAT and GRE.", color = Color(0xFFB8C5E2), fontSize = 15.sp, lineHeight = 23.sp)
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Continue as", color = Color.White, fontWeight = FontWeight.SemiBold)
            RoleButton("Learner", "Explore lessons, quizzes and study plans", Icons.Default.School) { onRole("Learner") }
            RoleButton("Creator / Teacher", "Create and manage learning content", Icons.Default.AddCircle) { onRole("Creator") }
            TextButton(onClick = onExplore, modifier = Modifier.fillMaxWidth()) {
                Text("Explore demo without signing in", color = Color(0xFFB8C5E2))
            }
        }
    }
}

@Composable
fun RoleButton(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(16.dp)).clickable { onClick() }.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = Blue, modifier = Modifier.size(28.dp))
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(title, color = Ink, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, color = Muted, fontSize = 12.sp)
        }
        Text("›", color = Blue, fontSize = 28.sp)
    }
}

@Composable
fun LoginScreen(role: String, email: String, password: String, onEmail: (String) -> Unit,
                onPassword: (String) -> Unit, onBack: () -> Unit, onSubmit: () -> Unit, onSwitch: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
        Spacer(Modifier.height(18.dp))
        Text(if (role == "Creator") "Teacher studio" else "Welcome back", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Ink)
        Text(if (role == "Creator") "Sign in to create and share learning resources." else "Sign in to continue your prep journey.", color = Muted)
        Spacer(Modifier.height(28.dp))
        Text("Email address", color = Ink, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(value = email, onValueChange = onEmail, modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("you@example.com") }, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
        Spacer(Modifier.height(14.dp))
        Text("Password", color = Ink, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(value = password, onValueChange = onPassword, modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter your password") }, singleLine = true, visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(22.dp))
        Button(onClick = onSubmit, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp)) {
            Text("Sign in as $role")
        }
        TextButton(onClick = onSwitch, modifier = Modifier.fillMaxWidth()) {
            Text(if (role == "Learner") "Are you a teacher? Creator login" else "Looking to learn? Learner login")
        }
        Text("Demo build: secure account creation and password recovery need a backend.", color = Muted, fontSize = 12.sp)
    }
}

@Composable
fun HomeScreen(role: String, notice: String, onCourse: (String) -> Unit, onCreate: () -> Unit, onProfile: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize().padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Spacer(Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("QUANT HOUR", color = Blue, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                    Text(if (role == "Creator") "Creator dashboard" else "Let’s get your score up.", color = Ink, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }
                TextButton(onClick = onProfile) { Text("Profile") }
            }
        }
        if (notice.isNotBlank()) item {
            Text(notice, color = Ink, fontSize = 12.sp, modifier = Modifier.fillMaxWidth().background(Color(0xFFE5EBFF), RoundedCornerShape(12.dp)).padding(12.dp))
        }
        item {
            Column(Modifier.fillMaxWidth().background(Navy, RoundedCornerShape(22.dp)).padding(20.dp)) {
                Text(if (role == "Creator") "YOUR TEACHING SPACE" else "YOUR STUDY SPACE", color = Color(0xFF9FB5FF), fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text(if (role == "Creator") "Turn your expertise into progress." else "Small practice. Big progress.", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text(if (role == "Creator") "Build lessons, quizzes and courses for test takers." else "Choose your exam and continue with a focused plan.", color = Color(0xFFCAD4ED), fontSize = 14.sp)
                if (role == "Creator") {
                    Spacer(Modifier.height(14.dp))
                    Button(onClick = onCreate, colors = ButtonDefaults.buttonColors(containerColor = Blue)) { Icon(Icons.Default.AddCircle, null); Spacer(Modifier.width(8.dp)); Text("Create content") }
                }
            }
        }
        item { Text("Explore exam prep", color = Ink, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
        item { CourseCard("GRE", "Graduate Record Examination", "Quant • Verbal • Analytical Writing", "12 lessons · 4 practice sets", onClick = { onCourse("GRE") }) }
        item { CourseCard("SAT", "College admission preparation", "Math • Reading & Writing", "10 lessons · 5 practice sets", onClick = { onCourse("SAT") }) }
        item { CourseCard("GAT", "Graduate Assessment Test", "Quantitative • Verbal • Analytical", "8 lessons · 3 practice sets", onClick = { onCourse("GAT") }) }
        item {
            Row(Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(16.dp)).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Quiz, null, tint = Blue, modifier = Modifier.size(30.dp))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(if (role == "Creator") "Content overview" else "Daily practice", fontWeight = FontWeight.Bold, color = Ink)
                    Text(if (role == "Creator") "Track lessons and quiz drafts in one place." else "Try a short question set and review explanations.", color = Muted, fontSize = 12.sp)
                }
            }
        }
        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
fun CourseCard(code: String, title: String, topics: String, meta: String, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().background(Color.White, RoundedCornerShape(18.dp)).clickable { onClick() }.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(54.dp).background(Color(0xFFEAF0FF), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
            Text(code, color = Blue, fontWeight = FontWeight.Black, fontSize = if (code.length > 3) 12.sp else 16.sp)
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(title, color = Ink, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(topics, color = Muted, fontSize = 12.sp)
            Spacer(Modifier.height(5.dp))
            Text(meta, color = Blue, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
        Text("›", color = Blue, fontSize = 28.sp)
    }
}

@Composable
fun CourseScreen(course: String, onBack: () -> Unit) {
    var tab by remember { mutableStateOf("Lessons") }
    var answer by remember { mutableStateOf(-1) }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
        Text("$course preparation", color = Ink, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Build a strong foundation with bite-sized learning.", color = Muted)
        Spacer(Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Lessons", "Practice", "Study plan").forEach { item ->
                FilterChip(selected = tab == item, onClick = { tab = item }, label = { Text(item) })
            }
        }
        Spacer(Modifier.height(16.dp))
        if (tab == "Lessons") {
            LessonRow("01", "Understand the question", "Concept lesson · 8 min")
            LessonRow("02", "Core strategies and shortcuts", "Video lesson · 12 min")
            LessonRow("03", "Worked examples", "Guided practice · 10 min")
        } else if (tab == "Study plan") {
            Text("Suggested 4-week plan", color = Ink, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            listOf("Week 1 · Review core concepts", "Week 2 · Practice topic sets", "Week 3 · Timed mixed questions", "Week 4 · Mock tests and review").forEach {
                LessonRow("✓", it, "Suggested milestone")
            }
        } else {
            Text("Quick practice", color = Ink, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text("If 3x + 5 = 20, what is x?", color = Ink, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            listOf("A. 3", "B. 5", "C. 8", "D. 15").forEachIndexed { index, option ->
                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp).background(if (answer == index) Color(0xFFE5EBFF) else Color.White, RoundedCornerShape(12.dp)).clickable { answer = index }.padding(14.dp)) {
                    Text(option, color = Ink)
                }
            }
            if (answer >= 0) Text(if (answer == 1) "Correct! 3x = 15, so x = 5." else "Not quite. Subtract 5 from both sides, then divide by 3.", color = if (answer == 1) Color(0xFF167A4A) else Color(0xFFB45309), modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun LessonRow(number: String, title: String, subtitle: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp).background(Color.White, RoundedCornerShape(14.dp)).padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(number, color = Blue, fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, color = Ink, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = Muted, fontSize = 12.sp)
        }
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.PlayCircle, null, tint = Blue)
    }
}

@Composable
fun CreatorScreen(onBack: () -> Unit, onPublish: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("GRE") }
    var kind by remember { mutableStateOf("Lesson") }
    var description by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
        Text("Create content", color = Ink, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Prepare a lesson or practice resource for your learners.", color = Muted)
        Spacer(Modifier.height(20.dp))
        Text("Content title", color = Ink, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(title, { title = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("e.g. Ratios and proportions") }, singleLine = true)
        Spacer(Modifier.height(12.dp))
        Text("Exam", color = Ink, fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("GRE", "SAT", "GAT").forEach { FilterChip(course == it, { course = it }, label = { Text(it) }) } }
        Spacer(Modifier.height(8.dp))
        Text("Content type", color = Ink, fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("Lesson", "Quiz", "Study notes").forEach { FilterChip(kind == it, { kind = it }, label = { Text(it) }) } }
        Spacer(Modifier.height(12.dp))
        Text("Description / lesson content", color = Ink, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(description, { description = it }, modifier = Modifier.fillMaxWidth().weight(1f), placeholder = { Text("Add learning objectives, explanations or questions…") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = onPublish, modifier = Modifier.fillMaxWidth().height(52.dp), enabled = title.isNotBlank(), shape = RoundedCornerShape(14.dp)) {
            Text("Save draft")
        }
        Text("Demo only: content is not uploaded to a server yet.", color = Muted, fontSize = 11.sp, modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun ProfileScreen(role: String, onBack: () -> Unit, onRole: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
        Text("Account", color = Ink, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        Text("Current mode", color = Muted)
        Text(role, color = Ink, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(18.dp))
        OutlinedButton(onClick = onRole, modifier = Modifier.fillMaxWidth()) { Text("Switch to ${if (role == "Learner") "Creator" else "Learner"} mode") }
        Spacer(Modifier.height(10.dp))
        Text("Quant Hour · GAT / SAT / GRE", color = Muted, fontSize = 12.sp)
    }
}
