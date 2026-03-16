import { motion } from "motion/react";
import { ChevronRight } from "lucide-react";

export function ServiceCard({ service }) {
  const IconComponent = service.icon;

  return (
    <motion.div
      whileHover={{ y: -10 }}
      className="group bg-white rounded-2xl overflow-hidden border border-gray-100 shadow-sm hover:shadow-xl transition-all duration-300"
    >
      <div className="h-64 relative overflow-hidden">
        <img
          src={service.image}
          alt={service.title}
          className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110"
        />
        <div className="absolute top-4 left-4 bg-white/90 backdrop-blur px-3 py-1 rounded-full flex items-center gap-2 text-xs font-bold text-[#2D5A27]">
          <IconComponent size={20} /> {service.badge}
        </div>
      </div>
      <div className="p-8">
        <h3 className="text-2xl font-crimson-text text-[#2D5A27] mb-3">
          {service.title}
        </h3>
        <div className="text-gray-500 font-light leading-relaxed">
          {service.description}
        </div>
        {service.extraContent}
        <button className="mt-6 flex items-center gap-2 text-[#A68A64] font-medium group-hover:gap-3 transition-all">
          {service.buttonText || "Saber más"} <ChevronRight size={16} />
        </button>
      </div>
    </motion.div>
  );
}
