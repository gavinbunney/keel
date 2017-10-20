/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.spinnaker.keel.controllers

import com.netflix.spinnaker.keel.Intent
import com.netflix.spinnaker.keel.IntentLauncher
import com.netflix.spinnaker.keel.IntentStatus
import com.netflix.spinnaker.keel.model.UpsertIntentRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.ws.rs.QueryParam

@RestController
@RequestMapping("/intents")
class IntentController
@Autowired constructor(
  private val orcaIntentLauncher: IntentLauncher<*>,
  private val dryRunIntentLauncher: IntentLauncher<*>
) {

  @RequestMapping(method = arrayOf(RequestMethod.PUT))
  fun upsertIntent(@RequestBody intent: UpsertIntentRequest): Any {
    // TODO rz - validate intents
    // TODO rz - calculate graph

    if (intent.dryRun) {
      return intent.intents.map { dryRunIntentLauncher.launch(it) }
    }
    // TODO rz - calculate graph, store into front50

    intent.intents.forEach { orcaIntentLauncher.launch(it) }

    return intent
  }

  @RequestMapping(method = arrayOf(RequestMethod.GET))
  fun getIntents(@QueryParam("statuses") statuses: List<IntentStatus>): List<Intent<*>> = TODO()

  @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
  fun getIntent(@PathVariable("id") id: String): Intent<*>? = TODO()

  @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.DELETE))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteIntent(@RequestParam("status", defaultValue = "CANCELED") status: IntentStatus): Nothing = TODO()

  @RequestMapping(value = "/{id}/history", method = arrayOf(RequestMethod.GET))
  fun getIntentHistory(@PathVariable("id") id: String): List<Any> = TODO()
}
