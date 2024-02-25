package com.codelabs.agrimate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.KeyboardAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.codelabs.agrimate.R
import com.codelabs.agrimate.ui.theme.Green100
import com.codelabs.agrimate.ui.theme.GreyScale1000
import com.codelabs.agrimate.ui.theme.GreyScale200
import com.codelabs.agrimate.ui.theme.GreyScale400
import com.codelabs.agrimate.ui.theme.GreyScale500
import com.codelabs.agrimate.ui.theme.GreyScale600
import com.codelabs.agrimate.ui.theme.GreyScale700
import com.codelabs.agrimate.ui.theme.GreyScale800
import com.codelabs.agrimate.ui.theme.Red1
import com.codelabs.agrimate.ui.theme.RedStatus
import com.codelabs.agrimate.ui.theme.md_theme_light_error
import com.codelabs.agrimate.utils.DateUtils
import com.codelabs.agrimate.utils.TimeUtils
import com.codelabs.core.BuildConfig

@Composable
fun AGInputLayout(
    modifier: Modifier = Modifier,
    label: String,
    isRequired: Boolean = false,
    content: @Composable (() -> Unit)
) {
    Column(modifier = modifier) {
        Text(modifier = Modifier, text = buildAnnotatedString {
            append(label)
            if (isRequired) {
                withStyle(style = SpanStyle(color = Red1)) {
                    append(" *")
                }
            }
        }, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
        content()
    }
}

@Composable
fun AGInputText(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (v: String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(12.dp),
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    OutlinedTextField(
        modifier = modifier,
        value = value, onValueChange = onValueChange,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = readOnly,
        supportingText = supportingText,
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = GreyScale200,
            focusedBorderColor = Green100,
            unfocusedBorderColor = GreyScale700,
            unfocusedTrailingIconColor = GreyScale600,
            focusedTrailingIconColor = GreyScale600,
            unfocusedLeadingIconColor = GreyScale600,
            focusedLeadingIconColor = GreyScale600,
            unfocusedContainerColor = GreyScale1000,
            unfocusedTextColor = GreyScale200,
            focusedTextColor = GreyScale200,
            unfocusedPlaceholderColor = GreyScale500,
            focusedPlaceholderColor = GreyScale500,
            focusedContainerColor = GreyScale1000,
            errorContainerColor = GreyScale1000,
            errorBorderColor = RedStatus
        ),
    )
}

interface AGSelectInputProvider {
    val label: String
    val value: String
}

@Composable
fun <T : AGSelectInputProvider> AGSelectInputWithSearch(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (T) -> Unit,
    onClick: () -> Unit = {},
    label: String,
    placeholder: String,
    options: List<T>,
    enabled: Boolean = true,
    message: String? = null,
    isError: Boolean = false,
    isLoading: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null
) {
    var isExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val filteredOptions = options.filter {
        it.label.contains(searchQuery, ignoreCase = true)
    }

    fun handleOnDismiss(v: Boolean) {
        isExpanded = v
        searchQuery = ""
    }

    Box(modifier = modifier.clip(RoundedCornerShape(12.dp))) {
        AGInputText(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {},
            placeholder = { Text(text = "-- $placeholder --") },
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null
                    )
                }
            },
            enabled = enabled,
            isError = isError,
            supportingText = supportingText,
        )
        Box(modifier = Modifier
            .matchParentSize()
            .alpha(0f)
            .clickable(enabled = enabled) {
                isExpanded = true
                onClick()
            })
    }

    if (isExpanded) {
        Dialog(onDismissRequest = { isExpanded = false }) {
            Surface(
                modifier = Modifier.sizeIn(maxHeight = 640.dp),
                shadowElevation = 4.dp,
                tonalElevation = 4.dp,
                color = GreyScale1000,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text(text = "Cari $label") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        colors = TextFieldDefaults.colors(
                            cursorColor = GreyScale200,
                            focusedContainerColor = GreyScale1000,
                            unfocusedContainerColor = GreyScale1000,
                            unfocusedTextColor = GreyScale200,
                            focusedTextColor = GreyScale200,
                            unfocusedPlaceholderColor = GreyScale500,
                            focusedPlaceholderColor = GreyScale500,
                        )
                    )
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        if (filteredOptions.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isLoading) {
                                        CircularProgressIndicator()
                                    } else {
                                        Text(
                                            text = message ?: "$label Tidak Ditemukan",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                        itemsIndexed(filteredOptions) { index, option ->
                            DropdownMenuItem(text = { Text(text = option.label) }, onClick = {
                                onValueChange(option)
                                handleOnDismiss(false)
                            }, modifier = Modifier.background(GreyScale1000))
                            if (index < filteredOptions.lastIndex) {
                                Divider(modifier = Modifier)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AGInputId(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (v: String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Default,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
) {
    AGInputText(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        singleLine = true,
        isError = isError,
        supportingText = supportingText
    )
}

@Composable
fun AGInputEmail(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (v: String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Default,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
) {
    AGInputText(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = KeyboardType.Email),
        singleLine = true,
        isError = isError,
        supportingText = supportingText
    )
}

@Composable
fun AGInputPhoneNumber(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (v: String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Default,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
) {
    AGInputText(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = KeyboardType.Phone),
        singleLine = true,
        isError = isError,
        supportingText = supportingText
    )
}

@Composable
fun AGInputPassword(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (v: String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Default,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
) {
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    AGInputText(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction, keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = if (passwordVisible) "sembunyikan password" else "tampilkan password"
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        isError = isError,
        supportingText = supportingText
    )
}

@Composable
fun AGInputFullAddress(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (v: String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Default,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
) {
    AGInputText(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        placeholder = { Text(text = placeholder) },
        minLines = 3,
        isError = isError,
        supportingText = supportingText
    )
}

@Composable
fun AGInputSearch(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Search,
) {
    AGInputText(modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        placeholder = { Text(text = placeholder) },
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search, contentDescription = null, tint = GreyScale600
            )
        })
}

@Composable
fun <T : AGSelectInputProvider> AGInputWithSelectSuffix(
    modifier: Modifier = Modifier,
    value: String,
    onUnitChange: (T) -> Unit,
    onValueChange: (v: String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Default,
    suffix: String,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Decimal,
    supportingText: @Composable (() -> Unit)? = null,
    listSuffix: List<T>
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        AGInputText(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction, keyboardType = keyboardType
            ),
            singleLine = true,
            isError = isError,
            supportingText = supportingText
        )
        Box(modifier = Modifier.align(alignment = Alignment.TopEnd)) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .defaultMinSize(minWidth = 96.dp)
                    .widthIn(max = 120.dp)
                    .padding(top = 7.dp, bottom = 7.dp, end = 6.dp, start = 6.dp),
                shape = RoundedCornerShape(8.dp),
                color = Green100
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            expanded = true
                        }
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = suffix, style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                        ), textAlign = TextAlign.Center
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
            if (listSuffix.isNotEmpty()) {
                AGActionDropdown(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    listSuffix.forEach {
                        AGDropdownMenuItem(text = it.label, onClick = {
                            expanded = false
                            onUnitChange(it)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun AGInputWithSuffix(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Default,
    suffix: String,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Decimal,
    supportingText: @Composable (() -> Unit)? = null

) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        AGInputText(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction, keyboardType = keyboardType
            ),
            singleLine = true,
            isError = isError,
            supportingText = supportingText
        )
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .defaultMinSize(minWidth = 96.dp),
            color = Color(0xFFEAEAEA),
            shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    suffix, style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF808080),
                    )
                )
            }
        }
    }
}

@Composable
fun AGInputWithPrefix(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Default,
    prefix: String,
    keyboardType: KeyboardType = KeyboardType.Decimal,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Surface(
            modifier = Modifier.fillMaxHeight(),
            color = Color(0xFFEAEAEA),
            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    prefix, style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF808080),
                    )
                )
            }
        }
        AGInputText(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder) },
            shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction, keyboardType = keyboardType
            ),
            singleLine = true,
            isError = isError,
            visualTransformation = visualTransformation
        )
    }
}

@Composable
fun AGInputBox(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector = Icons.Default.Add,
    onClick: () -> Unit,
) {
    Box(modifier = modifier
        .drawBehind {
            drawRoundRect(
                color = GreyScale500, style = Stroke(
                    width = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(2.dp.toPx(), 2.dp.toPx()), 0f,
                    ), cap = StrokeCap.Round
                ), cornerRadius = CornerRadius(12.dp.toPx())
            )
            drawRoundRect(
                color = GreyScale800, cornerRadius = CornerRadius(12.dp.toPx())
            )
        }
        .clip(RoundedCornerShape(12.dp))
        .clickable { onClick() }
        .padding(vertical = 12.dp, horizontal = 8.dp), contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(15.dp),
                imageVector = icon,
                contentDescription = null,
                tint = Green100
            )
            Text(
                text, style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GreyScale400,
                )
            )
        }
    }
}

@Composable
fun AGInputImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageUrl: String? = null,
    isUploading: Boolean = false,
    isError: Boolean = false,
) {
    Surface(
        modifier = modifier.drawBehind {
            drawRoundRect(
                color = if (isError) md_theme_light_error else Color(0XFFE0E0E0), style = Stroke(
                    width = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(8.dp.toPx(), 8.dp.toPx()), 0f,
                    ), cap = StrokeCap.Round
                ), cornerRadius = CornerRadius(12.dp.toPx())
            )
        }, shape = RoundedCornerShape(12.dp), color = GreyScale1000, onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 140.dp)
                .height(IntrinsicSize.Min), contentAlignment = Alignment.Center
        ) {
            if (isUploading) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 18.dp)
                        .zIndex(3f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(R.drawable.ic_upload),
                        contentDescription = null,
                        tint = GreyScale500
                    )
                    Spacer(Modifier.padding(bottom = 4.dp))
                    Text(
                        "Mengunggah...", style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 27.3.sp,
                            fontWeight = FontWeight.Medium,
                            color = GreyScale500,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            if (!imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    modifier = Modifier.fillMaxHeight(),
                    model = "${BuildConfig.API_AGRIMATE_BASE_URL}/farmer-land/thumbnail/$imageUrl",
                    contentDescription = "$imageUrl",
                    contentScale = ContentScale.Crop
                )
            }
            if (imageUrl.isNullOrEmpty() && !isUploading) {
                Column(
                    Modifier.padding(horizontal = 12.dp, vertical = 18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(R.drawable.icon_camera),
                        contentDescription = null,
                        tint = GreyScale500
                    )
                    Spacer(Modifier.padding(bottom = 4.dp))
                    Text(
                        "Klik disini", style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 27.3.sp,
                            fontWeight = FontWeight.Medium,
                            color = GreyScale500,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AGInputDate(
    modifier: Modifier = Modifier,
    value: Long?,
    onValueChange: (Long?) -> Unit,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null
) {
    var openDialog by remember { mutableStateOf(false) }

    Box(modifier.clip(RoundedCornerShape(12.dp))) {
        AGInputText(modifier = Modifier.fillMaxWidth(),
            value = value?.let { DateUtils.convertLongToTime(it) } ?: "",
            onValueChange = {},
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("DD/MM/YYYY", maxLines = 1, overflow = TextOverflow.Ellipsis) },
            trailingIcon = {
                IconButton(onClick = { openDialog = true }) {
                    Icon(imageVector = Icons.Outlined.CalendarToday, contentDescription = null)
                }
            },
            isError = isError,
            supportingText = supportingText
        )
        Box(modifier = Modifier
            .matchParentSize()
            .alpha(0f)
            .clickable { openDialog = true })
    }


    if (openDialog) {
        val datePickerState = rememberDatePickerState(value ?: DateUtils.currentTime())
        val confirmEnabled =
            remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
        DatePickerDialog(onDismissRequest = {
            openDialog = false
        }, confirmButton = {
            TextButton(
                onClick = {
                    openDialog = false
                    datePickerState.selectedDateMillis?.let { newValue ->
                        onValueChange(newValue)
                    }
                },
                enabled = confirmEnabled.value,
            ) {
                Text("Simpan")
            }
        }, dismissButton = {
            TextButton(
                onClick = { openDialog = false },
            ) {
                Text("Batal")
            }
        }, tonalElevation = 0.dp, shape = RoundedCornerShape(12.dp)
        ) {
            DatePicker(
                state = datePickerState, showModeToggle = true
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AGInputTime(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (v: String) -> Unit,
    title: String? = null,
) {
    var openDialog by remember { mutableStateOf(false) }

    Box(modifier.clip(RoundedCornerShape(12.dp))) {
        AGInputText(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {},
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text("00:00", maxLines = 1, overflow = TextOverflow.Ellipsis) },
            trailingIcon = {
                IconButton(onClick = { openDialog = true }) {
                    Icon(imageVector = Icons.Outlined.AccessTime, contentDescription = null)
                }
            })
        Box(modifier = Modifier
            .matchParentSize()
            .alpha(0f)
            .clickable { openDialog = true })
    }
    if (openDialog) {
        val timePickerState = rememberTimePickerState(
            is24Hour = true,
            initialHour = TimeUtils.parseHour(value),
            initialMinute = TimeUtils.parseMinute(value)
        )
        var isTimeInput by remember { mutableStateOf(false) }

        AGTimePickerDialog(onDismissRequest = {
            openDialog = false
        }, onCancel = {
            openDialog = false
        }, onConfirm = {
            onValueChange(TimeUtils.formatTime(timePickerState.hour, timePickerState.minute))
            openDialog = false
        }, toggle = {
            Icon(
                modifier = Modifier.clickable { isTimeInput = !isTimeInput },
                imageVector = if (isTimeInput) Icons.Outlined.AccessTime else Icons.Outlined.KeyboardAlt,
                contentDescription = null
            )
        }, title = title ?: "Waktu Kegiatan"
        ) {
            if (isTimeInput) {
                TimeInput(
                    state = timePickerState, colors = TimePickerDefaults.colors(
                        timeSelectorSelectedContainerColor = Color.Transparent,
                        timeSelectorUnselectedContainerColor = Color.Transparent,
                    )
                )
            } else {
                TimePicker(
                    state = timePickerState, colors = TimePickerDefaults.colors(
                        timeSelectorSelectedContainerColor = Color.Transparent,
                        timeSelectorUnselectedContainerColor = Color.Transparent,
                    )
                )
            }
        }
    }
}