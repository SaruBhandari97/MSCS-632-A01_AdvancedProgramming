"Define Ride superclass"
Object subclass: Ride [
    | rideID pickupLocation dropoffLocation distance |

    Ride class >> id: id pickup: pickup dropoff: drop distance: dist [
        ^ self new initializeWithID: id pickup: pickup dropoff: drop distance: dist
    ]

    initializeWithID: id pickup: pickup dropoff: drop distance: dist [
        rideID := id.
        pickupLocation := pickup.
        dropoffLocation := drop.
        distance := dist.
    ]

    fare [
        ^ 0  "base class returns 0"
    ]

    rideDetails [
        'Ride ID: ' display. rideID printNl.
        'Pickup: ' display. pickupLocation displayNl.
        'Dropoff: ' display. dropoffLocation displayNl.
        'Distance: ' display. distance printNl.
        'Fare: $' display. (self fare asFloat asString) displayNl.
    ]

    getDistance [ ^ distance ]
]

"Define StandardRide subclass"
Ride subclass: StandardRide [
    fare [
        ^ self getDistance * 2.0
    ]
]

"Define PremiumRide subclass"
Ride subclass: PremiumRide [
    fare [
        ^ self getDistance * 3.5
    ]
]

"Define Driver class"
Object subclass: Driver [
    | driverID name rating assignedRides |

    Driver class >> id: id name: n rating: r [
        ^ self new initializeWithID: id name: n rating: r
    ]

    initializeWithID: id name: n rating: r [
        driverID := id.
        name := n.
        rating := r.  "Store rating as integer to avoid float bugs"
        assignedRides := OrderedCollection new.
    ]

    addRide: ride [
        assignedRides add: ride.
    ]

    getDriverInfo [
        'Driver ID: ' display. driverID printNl.
        'Name: ' display. name displayNl.
        'Rating: ' display. ((rating asFloat / 10) asString) displayNl.
        'Assigned Rides: ' display. assignedRides size printNl.
    ]

    getAssignedRides [
        ^ assignedRides
    ]
]

"Define Rider class"
Object subclass: Rider [
    | riderID name requestedRides |

    Rider class >> id: id name: n [
        ^ self new initializeWithID: id name: n
    ]

    initializeWithID: id name: n [
        riderID := id.
        name := n.
        requestedRides := OrderedCollection new.
    ]

    requestRide: ride [
        requestedRides add: ride.
    ]

    viewRides [
        requestedRides do: [:ride | ride rideDetails. '' displayNl. ].
    ]
]

"Main Test Code"
| ride1 ride2 ride3 driver rider ridesCollection |

ride1 := StandardRide id: 101 pickup: 'Downtown' dropoff: 'Airport' distance: 10.
ride2 := PremiumRide id: 102 pickup: 'Mall' dropoff: 'Hotel' distance: 5.
ride3 := StandardRide id: 103 pickup: 'Station' dropoff: 'University' distance: 8.

driver := Driver id: 201 name: 'John Doe' rating: 48e.  "store rating as integer (4.8 * 10)"

driver addRide: ride1.
driver addRide: ride2.

rider := Rider id: 301 name: 'Alice'.
rider requestRide: ride1.
rider requestRide: ride2.
rider requestRide: ride3.

ridesCollection := OrderedCollection with: ride1 with: ride2 with: ride3.
ridesCollection do: [:r | r rideDetails. '' displayNl.].

driver getDriverInfo.
rider viewRides.
