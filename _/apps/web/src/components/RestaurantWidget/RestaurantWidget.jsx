import {
  Calendar,
  Clock,
  Users,
  Utensils,
  Sparkles,
  MessageSquare,
} from "lucide-react";
import { motion } from "motion/react";
import { SectionHeading } from "@/components/SectionHeading/SectionHeading";

export function RestaurantWidget({
  restaurantDate,
  setRestaurantDate,
  restaurantTime,
  setRestaurantTime,
  numGuests,
  setNumGuests,
  restaurantComments,
  setRestaurantComments,
  onReserve,
}) {
  return (
    <section className="py-20 bg-[#F8F9F8]">
      <div className="container mx-auto px-6">
        <SectionHeading
          title="Reserva tu Mesa"
          subtitle="Disfruta de nuestra propuesta gastronómica en un entorno privilegiado con vistas al campo."
        />

        <motion.div
          initial={{ opacity: 0, y: 30 }}
          whileInView={{ opacity: 1, y: 0 }}
          viewport={{ once: true }}
          className="bg-white rounded-2xl shadow-2xl p-6 md:p-8 border border-gray-100 max-w-5xl mx-auto"
        >
          <div className="flex items-center gap-2 mb-6">
            <Utensils className="text-[#A68A64]" size={24} />
            <h3 className="text-xl md:text-2xl font-crimson-text text-[#A68A64]">
              Restaurante Casa Club
            </h3>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-600 mb-2 flex items-center gap-2">
                <Calendar size={16} className="text-[#A68A64]" />
                Fecha
              </label>
              <input
                type="date"
                value={restaurantDate}
                onChange={(e) => setRestaurantDate(e.target.value)}
                min={new Date().toISOString().split("T")[0]}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#A68A64] focus:border-transparent transition-all"
              />
            </div>

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-600 mb-2 flex items-center gap-2">
                <Clock size={16} className="text-[#A68A64]" />
                Horario
              </label>
              <select
                value={restaurantTime}
                onChange={(e) => setRestaurantTime(e.target.value)}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#A68A64] focus:border-transparent transition-all appearance-none bg-white cursor-pointer"
              >
                <option value="lunch">Comida (13:00 - 16:00)</option>
                <option value="dinner">Cena (20:00 - 23:00)</option>
              </select>
            </div>

            <div className="flex flex-col">
              <label className="text-sm font-medium text-gray-600 mb-2 flex items-center gap-2">
                <Users size={16} className="text-[#A68A64]" />
                Número de comensales
              </label>
              <select
                value={numGuests}
                onChange={(e) => setNumGuests(e.target.value)}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#A68A64] focus:border-transparent transition-all appearance-none bg-white cursor-pointer"
              >
                {[...Array(12)].map((_, i) => (
                  <option key={i + 1} value={String(i + 1)}>
                    {i + 1} {i + 1 === 1 ? "Persona" : "Personas"}
                  </option>
                ))}
              </select>
            </div>

            <div className="flex items-end">
              <button
                onClick={onReserve}
                className="w-full bg-[#A68A64] text-white px-6 py-3 rounded-lg font-medium hover:bg-[#8e7555] transition-all transform hover:scale-105 shadow-md flex items-center justify-center gap-2"
              >
                <Sparkles size={20} />
                Solicitar Reserva
              </button>
            </div>

            <div className="flex flex-col md:col-span-2">
              <label className="text-sm font-medium text-gray-600 mb-2 flex items-center gap-2">
                <MessageSquare size={16} className="text-[#A68A64]" />
                Comentarios o peticiones especiales (opcional)
              </label>
              <textarea
                value={restaurantComments}
                onChange={(e) => setRestaurantComments(e.target.value)}
                placeholder="Alergias, intolerancias, celebración especial, zona preferida..."
                rows={3}
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#A68A64] focus:border-transparent transition-all resize-none"
              />
            </div>
          </div>

          <p className="text-xs text-gray-400 mt-6 text-center">
            * Las reservas están sujetas a disponibilidad. Recibirás una
            confirmación por email o teléfono.
          </p>
        </motion.div>
      </div>
    </section>
  );
}
