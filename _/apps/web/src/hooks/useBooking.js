import { useState } from "react";

export function useBooking() {
  const [bookingDate, setBookingDate] = useState("");
  const [numPlayers, setNumPlayers] = useState("2");
  const [timeSlot, setTimeSlot] = useState("morning");

  const handleBookingSearch = () => {
    console.log("Buscando disponibilidad:", {
      date: bookingDate,
      players: numPlayers,
      timeSlot: timeSlot,
    });

    alert(
      `Búsqueda de disponibilidad:\nFecha: ${bookingDate}\nJugadores: ${numPlayers}\nHorario: ${timeSlot === "morning" ? "Mañana" : "Tarde"}`,
    );
  };

  return {
    bookingDate,
    setBookingDate,
    numPlayers,
    setNumPlayers,
    timeSlot,
    setTimeSlot,
    handleBookingSearch,
  };
}
