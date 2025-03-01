Change Log
==========


1.9.0
-----

### Added

 - DateTime
     - Added `Instant.toZonedDateTime` extension to convert an `Instant` to a
       `ZonedDateTime` in a specific timezone without needing to do multiple
       conversions.
     - Added `FixedClock` class to provide a simple syntax for creating a
       clock with a static time. This is particularly useful in combination
       with the Zoned clock implementation, since an anonymous interface
       conversion is cumbersome to convert.

1.8.1
-----

### Fixed
     - Coroutines
         - Fixed `OngoingFlow.collectOn` implementation from throwing an error
           during collection.

1.8.0
-----

### Added

 - Coroutines
     - `OngoingFlow` construct for flows that do not end, along with applicable
       transformation operators.

1.7.0
-----

### Changed

 - Upgrade to Kotlin 2.0.0

1.6.0
-----

### Added

 - Coroutines
     - `runRetryable` method for retrying an operation with optional delay
       strategies such as exponential backoff.
     - `whenTrue` extension for collecting a flow only when a value is true.

### Changed

 - `coroutines` module now depends on the `standard` module for its API.

1.5.0
-----

### Added

 - New Module: `com.inkapplications.watermelon:data`
    - Added module for common data abstractions and structures
    - Added Data Transformer construct to data module for bidirectional
      data conversions.
    - Added Data Validator construct for verifying arbitrary data values.

1.4.0
-----

### Added

 - New module: `com.inkapplications.watermelon:datetime`
     - Added module for extensions to the [kotlinx.datetime] libraries.
     - Add a `ZonedDateTime` object for associating a `LocalTime`
       and `Timezone` together.
     - Add a `ZonedDate` object for associating a `LocalDate` and
       `Timezone` together.
     - Add a `ZonedTime` object for associating a `LocalTime` and
       `Timezone` together.
     - Add a `ZonedClock` interface that provides local times.

[kotlinx.datetime]: https://github.com/Kotlin/kotlinx-datetime

1.3.0
-----

### Added
 - Coroutines
     - Extend support for `combine` to support up to 10 flows.
 - Android
     - `Context.createNotificationBuilder` Support method for creating
       notification builders across android versions.
 - Platform Support
     - Added `linuxArm64` platform support.

1.2.0
-----

### Dependency Changes
 - Kotlin updated to 1.8.22
 - Android Tools Plugin upgraded to 8.1.1

1.1.0
-----

### Added
 - Coroutines
     - `combineApply` method on flows to run applies against the current
       flow for each item in a new flow.
 - Standard
     - `component6` through `component10` extensions added for `Array<T>` types.

1.0.1
-----

### Fixed
 - Standard
     - Fixed return type for `runRetryable` method.

1.0.0
-----

### Added
 - Android
     - `startActivity` extension that references via classname and
       intent builder.
     - `checkSelfPermissions` extension that associates an input of
       permissions with their current state.
     - `setDebouncingOnClickListener` to debounce view click listeners.
     - `stopPropagation`/`continuePropagation` extensions to run an
       action while returning true/false for Android's callbacks.
     - `PermissionGate` construct to simplify required permission requests.
     - `setVisibility`, `fadeIn` and `fadeOut` methods to simplify
        View animations.
 - Coroutines
     - `safeCollect` collector that ensures a coroutine context is active
       before calling its collector action.
     - `collectOn` extension to simplify collection on a specified coroutine
       scope at call-time.
     - `mapItems`, `filterItems`, `filterItemIsInstance`, `filterItemNotNull`
       extensions for manipulating collections within emitted flows.
     - `combineToPair`, `combineTriple` and `combineFlatten` extensions to
       simplify combining flow operations.
     - `mapItemsCatching`, `onItemFailure`, `filterItemSuccess` and
       `filterItemFailure` methods to simplify working with collections of
       Results emitted by a flow.
 - Standard
     - `mapArray` and `mapSet` extensions to map collections preserving
       the type.
     - `CompositeException` class to wrap multiple exceptions.
     - `throwCancels` extension on the `Result` class to re-throw coroutine
       cancellation exceptions.
     - `retry` function to execute a block of code multiple times if an
       exception is thrown.
