package com.example.cubipool.Interfaces

import com.example.cubipool.service.user.UserReservationsAvailables

interface OnReservationAvailableListener {
    fun onItemSelected(userReservationsAvailables: UserReservationsAvailables)
}