export function SectionHeading({ title, subtitle, centered = true }) {
  return (
    <div className={`mb-12 ${centered ? "text-center" : ""}`}>
      <h2
        className={`text-3xl md:text-4xl font-crimson-text text-[#2D5A27] mb-4`}
      >
        {title}
      </h2>
      {subtitle && (
        <p className="text-gray-600 max-w-2xl mx-auto text-lg font-light leading-relaxed">
          {subtitle}
        </p>
      )}
    </div>
  );
}
