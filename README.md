# floating-action-menu-android

A library for Android Floating Action Button. Upon tapped, a list of sub floating action buttons are shown.

## Sample usage

```Kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val onClick = View.OnClickListener {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    main_floating_action_menu
        ?.setMainButtonDrawableId(R.drawable.ic_add_black_24dp)
        ?.addSubFloatingActionButton(FloatingActionModel(R.drawable.ic_replay_black_16dp, R.color.purple, R.string.action_menu_1), onClick)
        ?.addSubFloatingActionButton(FloatingActionModel(R.drawable.ic_access_alarms_black_16dp, R.color.green, R.string.action_menu_2), onClick)
        ?.addSubFloatingActionButton(FloatingActionModel(R.drawable.ic_palette_black_16dp, R.color.red, R.string.action_menu_3), onClick)
        ?.addSubFloatingActionButton(FloatingActionModel(R.drawable.ic_library_add_black_16dp, R.color.blue, R.string.action_menu_4), onClick)
        ?.apply()
}
```

<img src="https://user-images.githubusercontent.com/26830868/53701267-2cbfd100-3dc9-11e9-996d-4fc5d36dae47.gif" width=400>
