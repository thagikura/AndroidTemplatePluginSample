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

import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import activities.bottomNavigationActivity.res.layout.navigationActivityMainXml
import activities.bottomNavigationActivity.res.menu.navigationXml
import activities.bottomNavigationActivity.res.navigation.mobileNavigationXml
import activities.bottomNavigationActivity.res.values.dimensXml
import activities.bottomNavigationActivity.res.values.stringsXml
import activities.bottomNavigationActivity.src.app_package.mainActivityJava
import activities.bottomNavigationActivity.src.app_package.mainActivityKt
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.android.tools.idea.wizard.template.impl.activities.common.generateManifest
import com.android.tools.idea.wizard.template.impl.activities.common.navigation.navigationDependencies
import com.android.tools.idea.wizard.template.impl.activities.common.navigation.saveFragmentAndViewModel
import java.io.File

fun RecipeExecutor.bottomNavigationActivityRecipe2(
        moduleData: ModuleTemplateData,
        activityClass: String,
        activityTitle: String,
        layoutName: String,
        packageName: String
) {
    val (projectData, srcOut, resOut) = moduleData
    val appCompatVersion = 28
    val useAndroidX = moduleData.projectTemplateData.androidXSupport
    val useMaterial2 = useAndroidX || hasDependency("com.google.android.material:material")
    val ktOrJavaExt = projectData.language.extension
    val generateKotlin = projectData.language == Language.Kotlin
    val isLauncher = true
    addAllKotlinDependencies(moduleData)

    addDependency("com.android.support:appcompat-v7:${appCompatVersion}.+")
    addDependency("com.android.support:design:${appCompatVersion}.+")
    addDependency("com.android.support.constraint:constraint-layout:+")

    generateManifest(
            moduleData = moduleData,
            activityClass = activityClass,
            activityTitle = activityTitle,
            packageName = packageName,
            isLauncher = isLauncher,
            hasNoActionBar = false,
            generateActivityTitle = true,
            requireTheme = false,
            useMaterial2 = true
    )

    val language = projectData.language
    saveFragmentAndViewModel(resOut, srcOut, language, packageName, "home", useAndroidX)
    saveFragmentAndViewModel(resOut, srcOut, language, packageName, "dashboard", useAndroidX)
    saveFragmentAndViewModel(resOut, srcOut, language, packageName, "notifications", useAndroidX)
    navigationDependencies(generateKotlin, useAndroidX, appCompatVersion ?: 28)
    save(mobileNavigationXml(packageName), resOut.resolve("navigation/mobile_navigation.xml"))

    open(resOut.resolve("navigation/mobile_navigation.xml"))

    copy(File("ic_dashboard_black_24dp.xml"), resOut.resolve("drawable/ic_dashboard_black_24dp.xml"))
    copy(File("ic_home_black_24dp.xml"), resOut.resolve("drawable/ic_home_black_24dp.xml"))
    copy(File("ic_notifications_black_24dp.xml"), resOut.resolve("drawable/ic_notifications_black_24dp.xml"))

    val mainActivity = when (projectData.language) {
        Language.Java -> mainActivityJava(activityClass, layoutName, packageName, useAndroidX, useMaterial2)
        Language.Kotlin -> mainActivityKt(activityClass, layoutName, packageName, useAndroidX, useMaterial2)
    }
    save(mainActivity, srcOut.resolve("${activityClass}.${ktOrJavaExt}"))
    save(navigationActivityMainXml(useMaterial2), resOut.resolve("layout/${layoutName}.xml"))

    mergeXml(dimensXml(), resOut.resolve("values/dimens.xml"))
    mergeXml(stringsXml(), resOut.resolve("values/strings.xml"))
    mergeXml(navigationXml(), resOut.resolve("menu/bottom_nav_menu.xml"))

    open(resOut.resolve("layout/${layoutName}.xml"))
}
