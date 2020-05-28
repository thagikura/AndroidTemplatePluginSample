/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package activities.bottomNavigationActivity

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.Constraint.CLASS
import com.android.tools.idea.wizard.template.Constraint.LAYOUT
import com.android.tools.idea.wizard.template.Constraint.NONEMPTY
import com.android.tools.idea.wizard.template.Constraint.UNIQUE
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API
import java.io.File

val bottomNavigationActivityTemplate2
  get() = template {
    revision = 1
    name = "Bottom Navigation Activity2"
    description = "Creates a new activity with bottom navigation2"
    minApi = MIN_API
    minBuildApi = MIN_API

    category = Category.Activity
    formFactor = FormFactor.Mobile
    screens = listOf(WizardUiContext.ActivityGallery, WizardUiContext.MenuEntry, WizardUiContext.NewProject, WizardUiContext.NewModule)

    lateinit var layoutName: StringParameter
    val activityClass = stringParameter {
      name = "Activity Name"
      default = "MainActivity"
      help = "The name of the activity class to create"
      constraints = listOf(CLASS, UNIQUE, NONEMPTY)
      suggest = { layoutToActivity(layoutName.value) }
    }

    layoutName = stringParameter {
      name = "Layout Name"
      default = "activity_main"
      help = "The name of the layout to create for the activity"
      constraints = listOf(LAYOUT, UNIQUE, NONEMPTY)
      suggest = { activityToLayout(activityClass.value) }
    }

    val activityTitle = stringParameter {
      name = "Title"
      default = "MainActivity"
      help = "The name of the activity. For launcher activities, the application title"
      visible = { false }
      constraints = listOf(NONEMPTY)
      suggest = { activityClass.value }
    }

    val packageName = defaultPackageNameParameter

    widgets(
      TextFieldWidget(activityClass),
      TextFieldWidget(layoutName),
      PackageNameWidget(packageName),
      LanguageWidget()
    )

    thumb { File("preview_analog.png") }

    recipe = { data: TemplateData ->
      bottomNavigationActivityRecipe2(
              data as ModuleTemplateData,
              activityClass.value,
              activityTitle.value,
              layoutName.value,
              packageName.value)
    }
  }

val defaultPackageNameParameter get() = stringParameter {
  name = "Package name"
  visible = { !isNewModule }
  default = "com.mycompany.myapp"
  constraints = listOf(Constraint.PACKAGE)
  suggest = { packageName }
}
