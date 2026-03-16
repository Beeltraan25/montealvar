import { motion } from "motion/react";
import { courseFeatures } from "@/data/courseFeatures";

export function CourseSection() {
  return (
    <section id="campo" className="py-24 bg-[#F8F9F8]">
      <div className="container mx-auto px-6">
        <div className="grid md:grid-cols-2 gap-16 items-center">
          <motion.div
            initial={{ opacity: 0, x: -30 }}
            whileInView={{ opacity: 1, x: 0 }}
            viewport={{ once: true }}
          >
            <h2 className="text-3xl md:text-5xl font-crimson-text text-[#2D5A27] mb-6">
              Un recorrido con carácter y naturaleza.
            </h2>
            <p className="text-lg text-gray-600 mb-8 leading-relaxed font-light">
              Diseñado para desafiar a los jugadores más experimentados y, al
              mismo tiempo, ser accesible y divertido para los principiantes.
              Sus calles anchas y amables contrastan con la exigencia de sus
              greenes rápidos y estratégicamente protegidos. Todo ello enmarcado
              por el espectacular entorno natural de Yebes y encinas
              centenarias.
            </p>

            <div className="grid grid-cols-2 gap-8">
              {courseFeatures.map((item, idx) => {
                const IconComponent = item.icon;
                return (
                  <div key={idx} className="flex flex-col">
                    <div className="mb-2">
                      <IconComponent className="text-[#A68A64]" />
                    </div>
                    <span className="font-bold text-[#2D5A27]">
                      {item.label}
                    </span>
                    <span className="text-sm text-gray-500">{item.desc}</span>
                  </div>
                );
              })}
            </div>
          </motion.div>

          <motion.div
            initial={{ opacity: 0, scale: 0.95 }}
            whileInView={{ opacity: 1, scale: 1 }}
            viewport={{ once: true }}
            className="relative rounded-2xl overflow-hidden shadow-2xl h-[500px]"
          >
            <img
              src="https://images.unsplash.com/photo-1535131749006-b7f58c99034b?q=80&w=2070&auto=format&fit=crop"
              alt="Campo de Golf Montealvar"
              className="w-full h-full object-cover"
            />
          </motion.div>
        </div>
      </div>
    </section>
  );
}
