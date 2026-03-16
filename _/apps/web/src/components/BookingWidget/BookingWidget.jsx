import { Calendar, Users, Sun, Sparkles } from "lucide-react";
import { motion } from "motion/react";

export function BookingWidget({
  bookingDate,
  setBookingDate,
  numPlayers,
  setNumPlayers,
  timeSlot,
  setTimeSlot,
  onSearch,
}) {
  return (
    <section className="relative -mt-16 z-30">
      <div className="container mx-auto px-6">
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6, delay: 0.5 }}
          className="bg-white rounded-2xl shadow-2xl p-6 md:p-8 border border-gray-100 max-w-5xl mx-auto"
        >
          <div className="flex items-center gap-2 mb-6">
            <Calendar className="text-[#2D5A27]" size={24} />
            <h3 className="text-xl md:text-2xl font-crimson-text text-[#2D5A27]">
              Reserva tu Greenfee
            </h3>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-4 gap-4 items-end">
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-600 mb-2 flex items-center gap-2">
                <Calendar size={16} className="text-[#A68A64]" />
                Fecha de juego
              </label>
              <input
                type="date"
                value={bookingDate}
                onChange={(e) => setBookingDate(e.target.value)}
                min={new Date().toISOString().split("T")[0]}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#2D5A27] focus:border-transparent transition-all"
              />
            </div>

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-600 mb-2 flex items-center gap-2">
                <Users size={16} className="text-[#A68A64]" />
                Número de jugadores
              </label>
              <select
                value={numPlayers}
                onChange={(e) => setNumPlayers(e.target.value)}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#2D5A27] focus:border-transparent transition-all appearance-none bg-white cursor-pointer"
              >
                <option value="1">1 Jugador</option>
                <option value="2">2 Jugadores</option>
                <option value="3">3 Jugadores</option>
                <option value="4">4 Jugadores</option>
              </select>
            </div>

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-600 mb-2 flex items-center gap-2">
                <Sun size={16} className="text-[#A68A64]" />
                Horario preferido
              </label>
              <select
                value={timeSlot}
                onChange={(e) => setTimeSlot(e.target.value)}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#2D5A27] focus:border-transparent transition-all appearance-none bg-white cursor-pointer"
              >
                <option value="morning">Mañana (8:00 - 14:00)</option>
                <option value="afternoon">Tarde (14:00 - 20:00)</option>
              </select>
            </div>

            <button
              onClick={onSearch}
              className="bg-[#2D5A27] text-white px-6 py-3 rounded-lg font-medium hover:bg-[#1e3d1a] transition-all transform hover:scale-105 shadow-md flex items-center justify-center gap-2 h-[50px]"
            >
              <Sparkles size={20} />
              Buscar Disponibilidad
            </button>
          </div>

          <p className="text-xs text-gray-400 mt-4 text-center">
            * La disponibilidad está sujeta a confirmación. Recibirás una
            confirmación por email.
          </p>
        </motion.div>
      </div>
    </section>
  );
}
