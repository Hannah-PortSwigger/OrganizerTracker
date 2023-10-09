# OrganizerTracker
Automatically send requests to the Organizer tool.

Automatically sends unique requests to Organizer.

Matching criteria: unique URL, in scope, from tool Proxy, Target or Repeater.

Will throw an error if there are no configured items in scope.

To modify, adjust criteria in `MyHttpHandler.matchesCriteria()`
