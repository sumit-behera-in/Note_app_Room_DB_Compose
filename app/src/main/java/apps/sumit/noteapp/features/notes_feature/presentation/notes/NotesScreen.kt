package apps.sumit.noteapp.features.notes_feature.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import apps.sumit.noteapp.features.notes_feature.domain.models.Note
import apps.sumit.noteapp.features.notes_feature.presentation.notes.components.NoteItem
import apps.sumit.noteapp.features.notes_feature.presentation.notes.components.OrderSection
import apps.sumit.noteapp.features.notes_feature.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    var bottomDrawerNote: Note? by remember {
        mutableStateOf(null)
    }



    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(scaffoldState) },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.clip(CircleShape),
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Your Note",
                    style = MaterialTheme.typography.headlineMedium
                )

                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = "Sort"
                    )
                }

            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn(
                    animationSpec = tween(
                        150,
                        0
                    )
                ) + slideInVertically(animationSpec = tween(450, 10)),
                exit = fadeOut(
                    animationSpec = tween(
                        250,
                        0
                    )
                ) + slideOutVertically(animationSpec = tween(450, 10))
            ) {

                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = {
                                    showBottomSheet = true
                                    bottomDrawerNote = note
                                },
                                onLongClick = {
                                    navController.navigate(
                                        route = Screen.AddEditNoteScreen.route +
                                                "?noteId=${note.id}&noteColor=${note.color}"
                                    )
                                }

                            ),
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = scaffoldState.showSnackbar(
                                    message = "Note Deleted",
                                    actionLabel = "Undo"
                                )

                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        },

                        )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }

        // bottom sheet

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Box(
                    modifier = Modifier
                        .background(Color(bottomDrawerNote?.color ?: Color.White.toArgb()))
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .clip(RectangleShape)
                        .combinedClickable(
                            onClick = {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }

                            },
                            onLongClick = {
                                navController.navigate(
                                    route = Screen.AddEditNoteScreen.route +
                                            "?noteId=${bottomDrawerNote?.id}&noteColor=${bottomDrawerNote?.color}"
                                )
                            }

                        ),
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .padding(end = 32.dp)
                    ) {

                        Text(
                            text = bottomDrawerNote?.title ?: "",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = bottomDrawerNote?.content ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            overflow = TextOverflow.Ellipsis
                        )

                    }

                }


            }
        }
    }

}