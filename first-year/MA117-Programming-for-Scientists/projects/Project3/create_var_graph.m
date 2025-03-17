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

orient("landscape");
saveas(1, "VarGraph.pdf");
