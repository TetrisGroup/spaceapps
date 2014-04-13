%% Simulation of astronouts gradual decrease in bone density
%assuming loosing 0.035 to 0.07% caclium everyday
%when fully implemented, the sample from astrnaout urine is what is taken
%away from initial caclum in body (instead of 
Ic = 1400; %initial calcium in body
time = 1:100;%days
 %calcium lost per day
Tc = 1:length(time); %current calcium in body
Close = 0.035 %astronaut calcium percentage lost per day
for i = 1:length(time)
    CloseR = (rand(1)*0.035+Close)*Ic %lose 0.035% to 0.07% calcium per day
    Tc(i) = Ic - CloseR; %current calcium in body - 
    Ic = Ic - CloseR; %taking away calcium loss from initial body calcium
    %Close = Close + 0.001; %assuming you lose calcium at a higher rate the longer you are in space
    if(Tc(i) < 0) %stopping the data from going negative
        Tc(i) = 0;
    end
end


plot(time,Tc);


%% pradicting software
%each line new "redraw" is taking into consideration the new information
line = [1:10:2000]';
N = length(time); %end of simulation
Nd = floor(N/10); %sampling periods
for i = Nd:Nd:N 
p = polyfit(time(1:i),Tc(1:i),1) %finding coefficients  
t = [time(1),time(end)]; %begining and end of plot
Pline = time(i);%vertical line position
y1 = polyval(p,t) %best fit line for a period of 10
plot(Pline,line,time(1:i),Tc(1:i),t,y1); 
pause(1)
end






