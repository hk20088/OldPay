module intervac{
	struct ReqVacBean{
		string sim;
		string productid;
		string payproductid;
		string producttype;
		string pseudocode;
		string price;
		string apid;
		string applid;
		string productname;
		string seq;
		string signtype;
	};
	struct ResVacBean{
		string resultcode;
		string resultdescription;
		string seq;
		string sn;
		
	};
	
	
	interface GameWoVacReq{
		ResVacBean getVacReq(ReqVacBean vbean);
	};
};