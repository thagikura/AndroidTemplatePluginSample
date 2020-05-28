package activities

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import activities.bottomNavigationActivity.bottomNavigationActivityTemplate2

class SamplePluginTemplateProviderImpl : WizardTemplateProvider() {

    override fun getTemplates(): List<Template> = listOf(
        bottomNavigationActivityTemplate2
    )
}