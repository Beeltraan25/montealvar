import { motion } from "motion/react";
import { SectionHeading } from "@/components/SectionHeading/SectionHeading";
import { pricingData } from "@/data/pricing";

export function PricingSection() {
  return (
    <section id="tarifas" className="py-24 bg-[#F8F9F8]">
      <div className="container mx-auto px-6">
        <div className="max-w-4xl mx-auto">
          <SectionHeading
            title="Únete a nuestro Club"
            subtitle="Juega a tu manera. Ofrecemos tarifas dinámicas para visitantes y planes de abono diseñados para que disfrutes de Montealvar durante todo el año."
          />

          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            className="bg-white rounded-3xl shadow-xl overflow-hidden"
          >
            <div className="p-1 pr-1 pl-1">
              <table className="w-full text-left">
                <thead>
                  <tr className="bg-[#2D5A27] text-white">
                    <th className="px-8 py-5 font-crimson-text text-xl">
                      Servicio
                    </th>
                    <th className="px-8 py-5 font-crimson-text text-xl text-right">
                      Tarifa
                    </th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-gray-100">
                  {pricingData.map((row, idx) => (
                    <tr
                      key={idx}
                      className="hover:bg-gray-50 transition-colors"
                    >
                      <td className="px-8 py-6 font-medium text-gray-700">
                        {row.item}
                      </td>
                      <td className="px-8 py-6 text-right font-bold text-[#2D5A27]">
                        {row.price}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </motion.div>

          <div className="mt-12 text-center">
            <button className="bg-[#A68A64] text-white px-10 py-4 rounded-full text-lg font-medium hover:bg-[#8e7555] transition-all transform hover:scale-105 shadow-lg">
              Consultar todas las Tarifas y Promociones
            </button>
            <p className="mt-6 text-gray-400 text-sm">
              * Precios sujetos a cambios según temporada y disponibilidad.
            </p>
          </div>
        </div>
      </div>
    </section>
  );
}
