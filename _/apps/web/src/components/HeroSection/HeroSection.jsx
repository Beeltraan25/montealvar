import { motion } from "motion/react";

export function HeroSection() {
  return (
    <section
      id="inicio"
      className="relative h-screen flex items-center justify-center overflow-hidden"
    >
      <div className="absolute inset-0">
        <img
          src="https://images.unsplash.com/photo-1587174486073-ae5e5cff23aa?q=80&w=2070&auto=format&fit=crop"
          alt="Montealvar Golf at Sunset"
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-black/40"></div>
      </div>

      <div className="relative container mx-auto px-6 text-center text-white">
        <motion.h1
          initial={{ opacity: 0, y: 30 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8 }}
          className="text-4xl md:text-7xl font-crimson-text mb-6 leading-tight"
        >
          Tu mejor golpe <br /> está por llegar.
        </motion.h1>
        <motion.p
          initial={{ opacity: 0, y: 30 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8, delay: 0.2 }}
          className="text-lg md:text-xl font-light mb-10 max-w-3xl mx-auto leading-relaxed opacity-90"
        >
          Descubre Montealvar Golf Club. Un recorrido Par 72 diseñado por Ramón
          Espinosa, donde el desafío deportivo se funde con la serenidad del
          Bosque de Valdenazar. A solo 30 minutos de Madrid.
        </motion.p>
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8, delay: 0.4 }}
          className="flex flex-col md:flex-row justify-center gap-4"
        >
          <button className="bg-[#2D5A27] text-white px-10 py-4 rounded-full text-lg font-medium hover:bg-[#1e3d1a] transition-all transform hover:scale-105">
            Reservar Greenfee
          </button>
          <button className="bg-white/10 backdrop-blur-md border border-white/30 text-white px-10 py-4 rounded-full text-lg font-medium hover:bg-white hover:text-[#2D5A27] transition-all transform hover:scale-105">
            Ver Abonos 2026
          </button>
        </motion.div>
      </div>
    </section>
  );
}
