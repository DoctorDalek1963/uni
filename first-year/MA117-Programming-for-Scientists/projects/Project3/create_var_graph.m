load "variance.data";

subplot(211);
semilogy(variance(:,1), variance(:,2), "r");
title("Variance of GeneralMatrix");
xlabel("Dimension");
ylabel("Variance");

subplot(212);
semilogy(variance(:,1), variance(:,3), "b");
title("Variance of TriMatrix");
xlabel("Dimension");
ylabel("Variance");

% NOTE: Uncomment these lines and run Octave wit FLTK or Qt to get slightly better
% graphs for final submission. Gnuplot only labels down to 10^-15 on the y axis
graphics_toolkit("gnuplot");
set(1, "visible", "off");

orient("landscape");
saveas(1, "VarGraph.pdf");
