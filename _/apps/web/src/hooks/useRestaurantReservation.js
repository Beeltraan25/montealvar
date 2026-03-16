import { useState } from "react";

export function useRestaurantReservation() {
  const [restaurantDate, setRestaurantDate] = useState("");
  const [restaurantTime, setRestaurantTime] = useState("lunch");
  const [numGuests, setNumGuests] = useState("1");
  const [restaurantComments, setRestaurantComments] = useState("");

  const handleRestaurantReservation = () => {
    console.log("Reservando mesa:", {
      date: restaurantDate,
      timeSlot: restaurantTime,
      guests: numGuests,
      comments: restaurantComments,
    });

    alert(
      `Reserva de mesa confirmada:\nFecha: ${restaurantDate}\nHorario: ${restaurantTime}\nComensales: ${numGuests}\nComentarios: ${restaurantComments}`,
    );
  };

  return {
    restaurantDate,
    setRestaurantDate,
    restaurantTime,
    setRestaurantTime,
    numGuests,
    setNumGuests,
    restaurantComments,
    setRestaurantComments,
    handleRestaurantReservation,
  };
}
