Change Log
==========

1.3.0
-----

### Added
 - Coroutines
     - Extend support for `combine` to support up to 10 flows.

1.2.0
-----

# Dependency Changes
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
