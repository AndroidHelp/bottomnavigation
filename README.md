# BottomNavigationView
Customizable library  
### developed in Kotlin

## Add Library
Using Maven
````
<dependency>
  <groupId>com.katariya.bottomnavigation</groupId>
  <artifactId>bottomnavigation</artifactId>
  <version>1.3.3</version>
  <type>pom</type>
</dependency>
````
Using Gradle
````
compile 'com.katariya.bottomnavigation:bottomnavigation:1.3.3'
````
Using Ivy
````
<dependency org='com.katariya.bottomnavigation' name='bottomnavigation' rev='1.3.3'>
  <artifact name='bottomnavigation' ext='pom' ></artifact>
</dependency>
````

## How to Use
 Add to XML
```xml
  <com.katariya.bottomnavigation.BottomNavigationView
         android:id="@+id/bottomView"
         android:layout_width="match_parent"
         android:layout_height="?android:actionBarSize"></com.katariya.bottomnavigation.BottomNavigationView>
```
### Note:
Add following line in gradle.properties
````
android.useAndroidX=true
android.enableJetifier=true
````
### Kotlin Support
#### In Module:app
````
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
````
In dependencies
````
implementation"org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
````

### In Project:ProjectName
In buildscript
````
 ext.kotlin_version = '1.3.31'
 ````
In dependencies
````
 classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
 ````


Add above properties in xml view
  1. tab_background_color : for adding/change tab background color
  2. tab_selected_item_background_color : for adding/change tab selected item color
  3. tab_selected_item_text_color:  for adding/change tab selected item text color
  4. tab_unselected_item_text_color: for adding/change tab unselected item text color
  5. tab_item_text_size: for adding/change tab item text size
  6. tab_badge_text_size: for adding/change tab item badge size
  7. tab_badge_background_color: for adding/change tab item badge background color
  8. tab_badge_item_text_color: for adding/change tab item badge item color
  9. tab_badge_background_border_color: for adding/change tab item badge background border color
  10. tab_badge_background_border_size: for adding.change tab item badge background border size
  11. tab_icon_selected_width: for adding/change tab icon width when selected
  12. tab_icon_selected_height: for adding/change tab icon height when selected
  13. tab_icon_unselected_width: for adding/change tab icon width when unselected
  14. tab_icon_unselected_height: for adding/change tab icon height when unselected
  15. tab_margin_in_icon_text_in_selected: for adding/change tab margin between icon and text when selected
  15. tab_margin_in_icon_text_in_unselected: for adding/change tab margin between icon and text when unselected
````
     app:tab_background_color="@color/colorPrimaryDark"
     app:tab_badge_background_height="14dp"
     app:tab_badge_background_width="14dp"
     app:tab_badge_text_size="3sp"
     app:tab_icon_selected_height="28dp"
     app:tab_icon_selected_width="28dp"
     app:tab_icon_unselected_height="24dp"
     app:tab_icon_unselected_width="24dp"
     app:tab_margin_in_icon_text_in_selected="2dp"
     app:tab_margin_in_icon_text_in_unselected="1dp"
     app:tab_selected_item_background_color="@color/colorPrimaryDark"
     app:tab_selected_item_text_color="@color/colorWhite"
     app:tab_unselected_item_text_color="@color/colorGray"
````
Initialization of BottomNavigationView

```
   private lateinit var bottomNavigationView: BottomNavigationView
                  
                    bottomNavigationView = findViewById(R.id.bottomView)
```

#### Add tab item in BottomNavigationView 
When you want to show only icon and title: 
<br />First icon will be for unselected state and second icon will be used for selected state

```
    bottomNavigationView.addChild(R.drawable.ic_chat_icon,R.drawable.ic_chat_icon, getString(R.string.title_chats))
    bottomNavigationView.addChild(R.drawable.ic_contacts_icon,R.drawable.ic_contacts_icon, getString(R.string.title_contacts))
    bottomNavigationView.addChild(R.drawable.ic_man_user_icon,R.drawable.ic_man_user_icon, getString(R.string.title_account))
```
When you want to show only icon:
<br />Add only icon and rest should be null 
````
     bottomNavigationView.addChild(R.drawable.ic_chat_icon, null, null)
     bottomNavigationView.addChild(R.drawable.ic_contacts_icon, null, null)
     bottomNavigationView.addChild(R.drawable.ic_man_user_icon, null, null)
````
When you want to show only name:
<br />Add only name and rest should be null 
````
     bottomNavigationView.addChild(null, null, getString(R.string.title_chats))
     bottomNavigationView.addChild(null, null, getString(R.string.title_contacts))
     bottomNavigationView.addChild(null, null, getString(R.string.title_account))
````
When you pass null for second icon and add first icon as well title
then icon will remain same after click 
````
 bottomNavigationView.addChild(R.drawable.ic_chat_icon, null, getString(R.string.title_chats))
````
#### Add Listener
````
 bottomNavigationView.initItemListener(this)

 override fun bottomNavigationViewItemClickListener(position: Int) {
               if (position == 0) textTitle.text = getString(R.string.title_chats)
               if (position == 1) textTitle.text = getString(R.string.title_contacts)
               if (position == 2) textTitle.text = getString(R.string.title_account)
               //     Toast.makeText(applicationContext, "" + position, Toast.LENGTH_SHORT).show()
            }
````

##### Change Font of tab item

````
    bottomNavigationView.updateFont("fonts/roboto_medium.ttf")
````
##### Add Badge in tab
Index: example pass 0 for first item and count is for badge quantity
````
     bottomNavigationView.setBadge(index, count)
````

###### Limitations
if you add more item(more then 5,6), some icon or text can be have size/cut/invisible issues 

![Alt text](/text.png?raw=true "Optional Title") 
![Alt text](/iconswhite.png?raw=true "Icons") 
![Alt text](/icons_color.png?raw=true "Custom icons") 

