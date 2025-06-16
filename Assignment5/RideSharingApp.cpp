/*
    Ride Sharing System - C++ Implementation
    Demonstrating: Encapsulation, Inheritance, and Polymorphism
*/

#include <iostream>
#include <vector>
#include <string>
#include <memory> 

using namespace std;

// ==============================
// Base Ride Class (Abstract)
// ==============================
class Ride {
protected:
    string rideID;
    string pickupLocation;
    string dropoffLocation;
    double distance;

public:
    Ride(string id, string pickup, string dropoff, double dist) :
        rideID(id), pickupLocation(pickup), dropoffLocation(dropoff), distance(dist) {}

    // Pure virtual method for fare calculation
    //(Polymorphism)
    virtual double fare() const = 0;

    // Virtual method to display ride details
    virtual void rideDetails() const {
        cout << "Ride ID: " << rideID << endl;
        cout << "Pickup Location: " << pickupLocation << endl;
        cout << "Dropoff Location: " << dropoffLocation << endl;
        cout << "Distance: " << distance << " miles" << endl;
    }

    // Virtual destructor
    virtual ~Ride() {}
};

// ==============================
// StandardRide Subclass
// ==============================
class StandardRide : public Ride {
public:
    // Constructor
    StandardRide(string id, string pickup, string dropoff, double dist) :
        Ride(id, pickup, dropoff, dist) {}

    // Overriding fare method
    double fare() const override {
        return distance * 2.0; 
    }

    // Overriding rideDetails method
    void rideDetails() const override {
        cout << "--- Standard Ride ---" << endl;
        Ride::rideDetails();
        cout << "Fare: $" << fare() << endl;
    }
};

// ==============================
// PremiumRide Subclass
// ==============================
class PremiumRide : public Ride {
public:
    // Constructor
    PremiumRide(string id, string pickup, string dropoff, double dist) :
        Ride(id, pickup, dropoff, dist) {}

    // Overriding fare method
    double fare() const override {
        return distance * 3.5;
    }

    // Overriding rideDetails method
    void rideDetails() const override {
        cout << "--- Premium Ride ---" << endl;
        Ride::rideDetails();
        cout << "Fare: $" << fare() << endl;
    }
};

// ==============================
// Driver Class (Encapsulation)
// ==============================
class Driver {
private:
    string driverID;
    string name;
    double rating;
    vector<shared_ptr<Ride>> assignedRides; 

public:
    // Constructor
    Driver(string id, string n, double r) : driverID(id), name(n), rating(r) {}

    // Public method to add rides
    void addRide(shared_ptr<Ride> ride) {
        assignedRides.push_back(ride);
    }

    // Public method to display driver info
    void getDriverInfo() const {
        cout << "Driver ID: " << driverID << ", Name: " << name << ", Rating: " << rating << endl;
        cout << "Total Assigned Rides: " << assignedRides.size() << endl;
    }
};

// ==============================
// Rider Class (Encapsulation)
// ==============================
class Rider {
private:
    string riderID;
    string name;
    vector<shared_ptr<Ride>> requestedRides; 

public:
    // Constructor
    Rider(string id, string n) : riderID(id), name(n) {}

    // Public method to request a ride
    void requestRide(shared_ptr<Ride> ride) {
        requestedRides.push_back(ride);
    }

    // Public method to view all requested rides
    void viewRides() const {
        cout << "Rider ID: " << riderID << ", Name: " << name << endl;
        for (const auto& ride : requestedRides) {
            ride->rideDetails();
            cout << endl;
        }
    }
};

// ==============================
// Main Function - Demonstrates Polymorphism
// ==============================
int main() {
    // Create rides using polymorphism
    shared_ptr<Ride> ride1 = make_shared<StandardRide>("R001", "Downtown", "Airport", 12.5);
    shared_ptr<Ride> ride2 = make_shared<PremiumRide>("R002", "Mall", "Hotel", 7.8);

    // Store rides in a polymorphic collection
    vector<shared_ptr<Ride>> rides = {ride1, ride2};

    // Create Driver and assign rides
    Driver driver("D001", "John Doe", 4.8);
    driver.addRide(ride1);
    driver.addRide(ride2);
    driver.getDriverInfo();

    cout << endl;

    // Create Rider and request rides
    Rider rider("U001", "Alice Smith");
    rider.requestRide(ride1);
    rider.requestRide(ride2);
    rider.viewRides();

    cout << "--- Polymorphic Ride Details ---" << endl;
    // Demonstrating polymorphism
    for (const auto& ride : rides) {
        ride->rideDetails();
        cout << endl;
    }

    return 0;
}
