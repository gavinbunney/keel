package com.netflix.spinnaker.keel.telemetry

import com.netflix.spinnaker.keel.api.ResourceKind
import com.netflix.spinnaker.keel.api.artifacts.ArtifactType
import java.time.Duration

sealed class TelemetryEvent

data class ResourceCheckSkipped(
  val kind: ResourceKind,
  val id: String,
  val skipper: String = "unknown"
) : TelemetryEvent()

data class ResourceCheckTimedOut(
  val kind: ResourceKind,
  val id: String,
  val application: String
) : TelemetryEvent()

data class ResourceLoadFailed(
  val ex: Throwable
) : TelemetryEvent()

data class ArtifactVersionUpdated(
  val name: String,
  val type: ArtifactType
) : TelemetryEvent()

data class ArtifactVersionApproved(
  val application: String,
  val deliveryConfigName: String,
  val environmentName: String,
  val artifactName: String,
  val artifactType: ArtifactType,
  val artifactVersion: String
) : TelemetryEvent()

data class EnvironmentsCheckTimedOut(
  val application: String,
  val deliveryConfigName: String
) : TelemetryEvent()

data class EnvironmentCheckComplete(
  val application: String,
  val deliveryConfigName: String,
  val duration: Duration
)

data class ArtifactCheckSkipped(
  val type: ArtifactType,
  val name: String,
  val reason: String = "unknown"
)

data class ArtifactCheckTimedOut(
  val name: String,
  val deliveryConfigName: String?
) : TelemetryEvent()

data class ArtifactVersionVetoed(
  val application: String,
) : TelemetryEvent()
