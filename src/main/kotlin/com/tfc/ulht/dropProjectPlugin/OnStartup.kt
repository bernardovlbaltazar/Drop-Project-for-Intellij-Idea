/*-
 * Plugin Drop Project
 * 
 * Copyright (C) 2022 Yash Jahit & Bernardo Baltazar
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

package com.tfc.ulht.dropProjectPlugin

import com.intellij.openapi.components.ComponentManager
import com.intellij.openapi.components.ComponentManagerEx
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.tfc.ulht.dropProjectPlugin.assignmentComponents.ListAssignment
import com.tfc.ulht.dropProjectPlugin.loginComponents.Authentication
import com.tfc.ulht.dropProjectPlugin.loginComponents.CredentialsController
import com.tfc.ulht.dropProjectPlugin.toolWindow.panel.ToolbarPanel
import com.intellij.openapi.components.service

class OnStartup : StartupActivity {

    override fun runActivity(project: Project) {

        val credentials = CredentialsController().retrieveStoredCredentials(Globals.PLUGIN_ID)
        if (credentials != null) {
            credentials.getPasswordAsString()
                ?.let { credentials.userName?.let { it1 -> Authentication().onStartAuthenticate(it1, it) } }
        }
        if (Authentication.alreadyLoggedIn){
            val projectSelectedAssignmentID = service<ProjectComponents>().getProjectSelectedAssignmentID()
            if (projectSelectedAssignmentID != null){
                Globals.selectedAssignmentID = projectSelectedAssignmentID
            }

            ListAssignment(false).get()
            ToolbarPanel.loggedInToolbar()
        }

    }


}