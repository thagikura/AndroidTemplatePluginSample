Android Template Plugin Sample

**This is not an official sample. The API may break at any point until Google officially announces it.**

As of Android Studio 4.1, the templates API was completely overhauled to use Intellij plugin extension point.
This project is to showcase how to add templates by using the extension point.

Star the [issue](https://issuetracker.google.com/issues/154531807) if you are interested in using the API in production. 

**Steps to install the plugin**

1. Build the plugin  `$ ./gradlew buildPlugin`
2. Install the plugin
   1. Launch Android Studio 4.1 or above
   2. Choose `File -> Settings`
   3. Select `Plugins` and click the gear icon at the top of the dialog
   4. Choose `Install Plugin from Disk…`
   5. Select the generated jar under `$PROJECT_DIR/build/lib/` created in the previous step
   6. Restart Android Studio
   7. Choose `File -> New -> New Project…` you should be able to see a template from your plugin
   <img src="screenshots/template-plugin-added.png" width="640px" >
   