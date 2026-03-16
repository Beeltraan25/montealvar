import React, { useState } from "react";
import { Navigation } from "@/components/Navigation/Navigation";
import { HeroSection } from "@/components/HeroSection/HeroSection";
import { BookingWidget } from "@/components/BookingWidget/BookingWidget";
import { CourseSection } from "@/components/CourseSection/CourseSection";
import { ServicesSection } from "@/components/ServicesSection/ServicesSection";
import { RestaurantWidget } from "@/components/RestaurantWidget/RestaurantWidget";
import { PricingSection } from "@/components/PricingSection/PricingSection";
import { Footer } from "@/components/Footer/Footer";
import { useScrolled } from "@/hooks/useScrolled";
import { useBooking } from "@/hooks/useBooking";
import { useRestaurantReservation } from "@/hooks/useRestaurantReservation";

export default function MontealvarGolfPage() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const scrolled = useScrolled(50);

  const {
    bookingDate,
    setBookingDate,
    numPlayers,
    setNumPlayers,
    timeSlot,
    setTimeSlot,
    handleBookingSearch,
  } = useBooking();

  const {
    restaurantDate,
    setRestaurantDate,
    restaurantTime,
    setRestaurantTime,
    numGuests,
    setNumGuests,
    restaurantComments,
    setRestaurantComments,
    handleRestaurantReservation,
  } = useRestaurantReservation();

  return (
    <div className="min-h-screen bg-white font-inter text-[#333333]">
      <Navigation
        isMenuOpen={isMenuOpen}
        setIsMenuOpen={setIsMenuOpen}
        scrolled={scrolled}
      />

      <HeroSection />

      <BookingWidget
        bookingDate={bookingDate}
        setBookingDate={setBookingDate}
        numPlayers={numPlayers}
        setNumPlayers={setNumPlayers}
        timeSlot={timeSlot}
        setTimeSlot={setTimeSlot}
        onSearch={handleBookingSearch}
      />

      <CourseSection />

      <ServicesSection />

      <RestaurantWidget
        restaurantDate={restaurantDate}
        setRestaurantDate={setRestaurantDate}
        restaurantTime={restaurantTime}
        setRestaurantTime={setRestaurantTime}
        numGuests={numGuests}
        setNumGuests={setNumGuests}
        restaurantComments={restaurantComments}
        setRestaurantComments={setRestaurantComments}
        onReserve={handleRestaurantReservation}
      />

      <PricingSection />

      <Footer />

      <style jsx global>{`
        @import url('https://fonts.googleapis.com/css2?family=Crimson+Text:ital,wght@0,400;0,600;0,700;1,400&family=Inter:wght@300;400;500;700&display=swap');
        
        .font-crimson-text {
          font-family: 'Crimson Text', serif;
        }
        
        .font-inter {
          font-family: 'Inter', sans-serif;
        }

        html {
          scroll-behavior: smooth;
        }

        select {
          background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 16 16'%3E%3Cpath fill='%23333' d='M8 11L3 6h10z'/%3E%3C/svg%3E");
          background-repeat: no-repeat;
          background-position: right 12px center;
          padding-right: 36px;
        }

        input[type="date"]::-webkit-calendar-picker-indicator {
          cursor: pointer;
          filter: opacity(0.6);
        }

        input[type="date"]::-webkit-calendar-picker-indicator:hover {
          filter: opacity(1);
        }
      `}</style>
    </div>
  );
}
