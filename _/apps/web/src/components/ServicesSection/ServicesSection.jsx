import { GraduationCap, Utensils, Heart, ExternalLink } from "lucide-react";
import { SectionHeading } from "@/components/SectionHeading/SectionHeading";
import { ServiceCard } from "./ServiceCard";

export function ServicesSection() {
  const services = [
    {
      icon: GraduationCap,
      badge: "Aprende y Perfecciona",
      image:
        "https://images.unsplash.com/photo-1613904940700-f9f303a27918?q=80&w=2070&auto=format&fit=crop",
      title: "Escuela de Golf",
      description: (
        <p>
          Tanto si quieres dar tus primeros golpes como si buscas bajar tu
          hándicap, nuestros profesionales se adaptan a ti. Ofrecemos clases
          individuales, cursos trimestrales y una divertida Escuela Infantil.
        </p>
      ),
    },
    {
      icon: Utensils,
      badge: "El Mejor Hoyo 19",
      image:
        "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?q=80&w=2070&auto=format&fit=crop",
      title: "Restaurante Casa Club",
      description: (
        <p>
          Relájate tras tu partida en nuestra terraza panorámica con vistas al
          campo. Disfruta de una cuidada propuesta gastronómica, menú del día y
          el ambiente perfecto para comentar la jornada.
        </p>
      ),
    },
    {
      icon: Heart,
      badge: "Celebraciones Inolvidables",
      image:
        "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?q=80&w=2070&auto=format&fit=crop",
      title: "Bodas y Eventos",
      description: (
        <>
          <p className="mb-4">
            Imagina celebrar el día más importante de tu vida en un entorno
            histórico único. Nuestra{" "}
            <strong className="text-[#2D5A27]">
              finca rehabilitada del siglo XVI
            </strong>{" "}
            combina la majestuosidad de los jardines del antiguo Monasterio con
            la elegancia de espacios exclusivos y villas de alojamiento. Un
            marco incomparable donde la historia se encuentra con tu historia.
          </p>
        </>
      ),
      extraContent: (
        <a
          href="https://www.bodas.net/fincas/montealvar--e30637?cmp=ADW-SEM-PMAX-VENUES&utm_source=google&utm_medium=paid-search&utm_campaign=VENUES-Pmax-B2C-Marketplace&utm_content=performance_cpm_search-ad_na_na&gclid=CjwKCAjw1N7NBhAoEiwAcPchp0WUZMqhGs9tfh0FdRofqMx8KvdXEAzPg3agU-4NRXLdBBzh_4I2MxoCU4cQAvD_BwE"
          target="_blank"
          rel="noopener noreferrer"
          className="mt-4 w-full flex items-center justify-center gap-2 bg-gradient-to-r from-[#E85C75] to-[#D65874] text-white px-6 py-3 rounded-full font-medium hover:from-[#D65874] hover:to-[#C44764] transition-all transform hover:scale-105 shadow-lg group-hover:shadow-xl"
        >
          <Heart size={18} className="fill-current" />
          Ver nuestro escaparate en Bodas.net
          <ExternalLink size={16} />
        </a>
      ),
      buttonText: "Más información",
    },
  ];

  return (
    <section id="servicios" className="py-24 bg-white">
      <div className="container mx-auto px-6">
        <SectionHeading
          title="Vive la experiencia Montealvar"
          subtitle="Mucho más que un club de golf. Un lugar donde la naturaleza y la exclusividad se encuentran."
        />

        <div className="grid md:grid-cols-3 gap-8">
          {services.map((service, idx) => (
            <ServiceCard key={idx} service={service} />
          ))}
        </div>
      </div>
    </section>
  );
}
