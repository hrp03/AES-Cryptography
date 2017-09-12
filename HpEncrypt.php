<?php 

/*

Created By : Himanshu Prajapati
Github : hrp03
Date : 24th June, 2017

*/

class HpEncrypt
{
    private $iv = '0p9o8i7u6y5t4r3e'; // change your vector 
    private $key = '/.,;p]-09ijh654f'; // change your key

    function __construct(){}

    function encrypt($str,$platform) 
	{
		$iv = $this->iv;
		$td = mcrypt_module_open('rijndael-128', '', 'cbc', $iv);
		mcrypt_generic_init($td, $this->key, $iv);
		$encrypted = mcrypt_generic($td, $str);
		mcrypt_generic_deinit($td);
		mcrypt_module_close($td);
		
		if($platform == "ios")
			return base64_encode($encrypted);
		
		return bin2hex($encrypted);
	}

	function decrypt($code,$platform) 
	{
		if($platform == "ios")
			$code = bin2hex(base64_decode(str_replace(" ","+",$code))); // if platform is ios to remove padding
		
		$code = $this->hex2bin($code);

		$iv = $this->iv;
		$td = mcrypt_module_open('rijndael-128', '', 'cbc', $iv);
		mcrypt_generic_init($td, $this->key, $iv);
		$decrypted = mdecrypt_generic($td, $code);
		mcrypt_generic_deinit($td);
		mcrypt_module_close($td);
		
		return utf8_encode(trim($decrypted));
	}

	protected function hex2bin($hexdata) 
	{
		$bindata = '';
		for ($i = 0; $i < strlen($hexdata); $i += 2) 
		{
			$bindata .= chr(hexdec(substr($hexdata, $i, 2)));
		}
		return $bindata;
	}
}

/*


Usage : 
$hpEncrypt = new HpEncrypt();
$hpEncrypt->encrypt(“plaintext”,”android”);
$hpEncrypt->decrypt($encryptedtext,”android”);

*/

?>
