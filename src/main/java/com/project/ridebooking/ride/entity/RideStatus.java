package com.project.ridebooking.ride.entity;

public enum RideStatus  {REQUESTED, DRIVER_ASSIGNED, DRIVER_ARRIVING, IN_PROGRESS, COMPLETED, CANCELLED;

    public boolean canTransitionTo(RideStatus next){
        return switch(this){
            case REQUESTED -> next == DRIVER_ASSIGNED || next == CANCELLED;

            case DRIVER_ASSIGNED -> next==DRIVER_ARRIVING || next == CANCELLED;

            case DRIVER_ARRIVING -> next==IN_PROGRESS || next == CANCELLED;

            case IN_PROGRESS -> next==COMPLETED;

            case COMPLETED, CANCELLED -> false;

        };
    }
};