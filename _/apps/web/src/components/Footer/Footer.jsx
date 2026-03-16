import {
  MapPin,
  Phone,
  Mail,
  Instagram,
  Facebook,
  Twitter,
} from "lucide-react";

export function Footer() {
  return (
    <footer id="contacto" className="bg-[#1A2E16] text-white pt-20 pb-10">
      <div className="container mx-auto px-6">
        <div className="grid md:grid-cols-4 gap-12 mb-16">
          <div className="col-span-2">
            <div className="text-3xl font-crimson-text mb-6">
              Montealvar{" "}
              <span className="text-sm font-light italic">Golf Club</span>
            </div>
            <p className="text-gray-400 max-w-sm mb-8 leading-relaxed">
              Naturaleza, Deporte y Exclusividad en el corazón de Guadalajara.
              Un espacio diseñado para el disfrute del golf en su estado más
              puro.
            </p>
            <div className="flex space-x-4">
              <a
                href="#"
                className="w-10 h-10 rounded-full bg-white/5 flex items-center justify-center hover:bg-[#A68A64] transition-all"
              >
                <Instagram size={20} />
              </a>
              <a
                href="#"
                className="w-10 h-10 rounded-full bg-white/5 flex items-center justify-center hover:bg-[#A68A64] transition-all"
              >
                <Facebook size={20} />
              </a>
              <a
                href="#"
                className="w-10 h-10 rounded-full bg-white/5 flex items-center justify-center hover:bg-[#A68A64] transition-all"
              >
                <Twitter size={20} />
              </a>
            </div>
          </div>

          <div>
            <h4 className="font-crimson-text text-xl mb-6 text-[#A68A64]">
              Contacto
            </h4>
            <ul className="space-y-4">
              <li className="flex items-start gap-3 text-gray-400">
                <MapPin size={20} className="mt-1 text-[#A68A64]" />
                <span>
                  Carretera de Horche a Yebes, s/n, 19141 Yebes, Guadalajara.
                </span>
              </li>
              <li className="flex items-center gap-3 text-gray-400">
                <Phone size={20} className="text-[#A68A64]" />
                <span>949 100 233</span>
              </li>
              <li className="flex items-center gap-3 text-gray-400">
                <Mail size={20} className="text-[#A68A64]" />
                <span>informacion@montealvar.com</span>
              </li>
            </ul>
          </div>

          <div>
            <h4 className="font-crimson-text text-xl mb-6 text-[#A68A64]">
              Enlaces
            </h4>
            <ul className="space-y-3 text-gray-400">
              <li>
                <a href="#" className="hover:text-white transition-colors">
                  Aviso Legal
                </a>
              </li>
              <li>
                <a href="#" className="hover:text-white transition-colors">
                  Política de Privacidad
                </a>
              </li>
              <li>
                <a href="#" className="hover:text-white transition-colors">
                  Cookies
                </a>
              </li>
              <li>
                <a href="#" className="hover:text-white transition-colors">
                  Reglamento del Club
                </a>
              </li>
            </ul>
          </div>
        </div>

        <div className="border-t border-white/10 pt-8 flex flex-col md:flex-row justify-between items-center text-sm text-gray-500">
          <p>© 2026 Montealvar Golf Club. Todos los derechos reservados.</p>
          <p className="mt-4 md:mt-0 italic">
            Diseñado con elegancia y naturaleza.
          </p>
        </div>
      </div>
    </footer>
  );
}
