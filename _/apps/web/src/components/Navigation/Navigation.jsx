import { Menu, X } from "lucide-react";
import { motion } from "motion/react";
import { navLinks } from "@/data/navigation";

export function Navigation({ isMenuOpen, setIsMenuOpen, scrolled }) {
  return (
    <nav
      className={`fixed w-full z-50 transition-all duration-300 ${scrolled ? "bg-white shadow-md py-3" : "bg-transparent py-5"}`}
    >
      <div className="container mx-auto px-6 flex justify-between items-center">
        <div
          className={`text-2xl font-crimson-text font-bold ${scrolled ? "text-[#2D5A27]" : "text-white"}`}
        >
          Montealvar{" "}
          <span className="text-sm block md:inline md:ml-2 font-light italic">
            Golf Club
          </span>
        </div>

        <div className="hidden md:flex space-x-8 items-center">
          {navLinks.map((link) => (
            <a
              key={link.name}
              href={link.href}
              className={`text-sm uppercase tracking-widest transition-colors ${scrolled ? "text-[#333333] hover:text-[#2D5A27]" : "text-white hover:text-gray-300"}`}
            >
              {link.name}
            </a>
          ))}
          <a
            href="#tarifas"
            className="bg-[#2D5A27] text-white px-6 py-2 rounded-full text-sm font-medium hover:bg-[#1e3d1a] transition-all"
          >
            Reservar
          </a>
        </div>

        <button
          className="md:hidden text-white"
          onClick={() => setIsMenuOpen(!isMenuOpen)}
        >
          {isMenuOpen ? (
            <X
              size={28}
              className={scrolled ? "text-[#2D5A27]" : "text-white"}
            />
          ) : (
            <Menu
              size={28}
              className={scrolled ? "text-[#2D5A27]" : "text-white"}
            />
          )}
        </button>
      </div>

      {isMenuOpen && (
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          className="md:hidden bg-white absolute top-full left-0 w-full shadow-xl py-8 px-6 flex flex-col space-y-4"
        >
          {navLinks.map((link) => (
            <a
              key={link.name}
              href={link.href}
              className="text-lg font-medium text-[#333333]"
              onClick={() => setIsMenuOpen(false)}
            >
              {link.name}
            </a>
          ))}
          <button className="bg-[#2D5A27] text-white py-3 rounded-lg font-medium">
            Reservar Greenfee
          </button>
        </motion.div>
      )}
    </nav>
  );
}
