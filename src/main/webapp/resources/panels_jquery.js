var selected_auction = null;
var selected_bid = null;
var selected_newitem = null;
var delay = 200;
var num_reg = /^[1-9]+[0-9]*$/;
var otherauction_view = true;

$(document).ready(
		/*************************START*************************/
		
		
		
		
	function(){
		$.ajax({
			url : 'useritems/'.concat(userId),
			success : function(data) {
				$('#1p_body').html(data);
			}
		});
		$.ajax({
			url : 'otherauctions/'.concat(userId),
			success : function(data) {
				$('#2p_body').html(data);
			}
		});
		$.ajax({
			url : 'othernewitems/'.concat(userId),
			success : function(data) {
				$('#3p_body').html(data);
			}
		});
		

		
		/******************MÁSODIK*PANEL*GOMB********************/
		$("#second_panel_button").click(
			function(){
				if($("#second_panel_button").val()=='My auctions') {
					$("#second_panel_button").text('Other auctions');
					$("#second_panel_button").val('Other auctions');
					selected_bid = null;
					$('#2p_foot').html(""); //DESCRIPTION MEGOLDÁS IDE
					$.ajax({
						url : 'userauctions/'.concat(userId),
						success : function(data) {
							$('#2p_body').html(data);
						}
					});
					otherauction_view = false;
				}
				else {
					
					$("#second_panel_button").text('My auctions');
					$("#second_panel_button").val('My auctions');
					$.ajax({
						url : 'otherauctions/'.concat(userId),
						success : function(data) {
							$('#2p_body').html(data);
						}
					});
					otherauction_view = true;
				}
		});
		
		/******************HARMADIK*PANEL*GOMB*******************/
		$("#third_panel_button").click(
			function(){
				if($("#third_panel_button").val()=='Vote') {
					$("#third_panel_button").text('Suggest new item');
					$("#third_panel_button").val('Suggest new item');
					$.ajax({
						url : 'othernewitems/'.concat(userId),
						success : function(data) {
							$('#3p_body').html(data);
							$('#3p_foot').html("<tr><td id='3p_description'></td></tr>");
						}
					});
				}
				else {
					$("#third_panel_button").text('Vote');
					$("#third_panel_button").val('Vote');
					selected_newitem = null;
					$('#3p_body').html("" +
							"<tr><td>Item name: </td><td><input type='text' id='newitemname'/></td></tr>" +
							"<tr><td>Description: </td><td><textarea rows='3' id='newitemdescription'/></td></tr>" +
							"<tr><td>Prize: </td><td><input type='number' id='newitemprize'/></td></tr>");
					
					$('#3p_foot').html("" +
							"<tr><td colspan='2'>" +
							"<input type='button' id='third_panel_button' onclick='suggestItem()' value='Suggest'/>" +
							"</td></tr>");
					
				}
		});
});

/******************ACITON*GOMBOK*******************/	
function createAuctionButton(userId, itemId) {
	
	if(selected_auction != itemId) {
		$('#auc_but'+selected_auction).css('color', 'white');
		$('#auc_but'+itemId).css('color', '#3399ff');
		selected_auction = itemId;
		$('#1p_foot').html("<tr><td>" +
				"<input type='number' name='prize' id='auction_starting_prize'/>" +
				"<input type='button' class='createAuctionButtonStyle' onclick="+pick+"createAuction('"+userId+"','"+itemId+"')"+pick+" value='Create auction' /></td></tr>");
	}
	else {
		$('#auc_but'+itemId).css('color', 'white');
		$('#1p_foot').html(""); //DESCRIPTION MEGOLDÁS IDE
		selected_auction = null;
	}
	
}

function createAuction(userId, itemId) {
	
	var number = $('#auction_starting_prize').val();
	
	if(number.match(num_reg)) {
		$.ajax({
			url : 'createAuction/'+userId+'/'+itemId+'/'+number,
			success : function(data) {
				if(data) {
					
				}
			}
		});
		$('#1p_foot').html("");
		$('#auc_but'+selected_auction).hide();
		selected_auction = null;
		if (otherauction_view) {
			setTimeout(function(){reloadOtherAuctions()}, delay);
		}
		else {
			setTimeout(function(){reloadUserAuctions()}, delay);
		}
		setTimeout(function(){reloadItems()}, delay);
	}
	else {
		$('#auction_starting_prize').val(0);
	}
}

function sellAuctionButton(auctionId) {
	sellAuction(auctionId);
}

function sellAuction(auctionId) {
	$.ajax({
		url : 'sellAuction/'+auctionId,
		success : function(data) {
			if(data) {
				//SIKERES ELADÁS VISSZAJELZÉSE
			}
		}
	});
	if (otherauction_view) {
		setTimeout(function(){reloadOtherAuctions()}, delay);
	}
	else {
		setTimeout(function(){reloadUserAuctions()}, delay);
	}
	setTimeout(function(){reloadItems()}, delay);
}

function bidAuctionButton(auctionId) {
	
	if(selected_bid != auctionId) {
		$('#bid_but'+selected_bid).css('color', 'white');
		$('#bid_but'+auctionId).css('color', '#3399ff');
		selected_bid = auctionId;
		$('#2p_foot').html("<tr><td>Bid: " +
				"<input type='number' name='bid' id='my_bid'/>" +
				"<input type='button' onclick="+pick+"bidAuction('"+userId+"','"+auctionId+"')"+pick+" value='Bid' /></td></tr>");
	}
	else {
		$('#bid_but'+auctionId).css('color', 'white');
		$('#2p_foot').html(""); //DESCRIPTION MEGOLDÁS IDE
		selected_bid = null;
	}
}

function bidAuction(userId,auctionId) {
	var number = $('#my_bid').val();
	
	if(number.match(num_reg)) {
		$.ajax({
			url : 'bidAuction/'+auctionId+'/'+userId+'/'+number,
			success : function(data) {
				if(data) {
					//SIKERES LICIT VISSZAJELZÉSE 
				}
			}
		});
		$('#2p_foot').html("");
		setTimeout(function(){reloadItems()}, delay);
		if (otherauction_view) {
			setTimeout(function(){reloadOtherAuctions()}, delay);
		}
		else {
			setTimeout(function(){reloadUserAuctions()}, delay);
		}
	}
	else {
		$('#my_bid').val(0);
	}
}

function deleteAuctionButton(auctionId) {
	$.ajax({
		url : 'deleteAuction/'+auctionId,
		success : function(data) {
			if(data) {
				//SIKERES TÖRLÉS VISSZAJELZÉSE
			}
		}
	});
	if (otherauction_view) {
		setTimeout(function(){reloadOtherAuctions()}, delay);
	}
	else {
		setTimeout(function(){reloadUserAuctions()}, delay);
	}
	setTimeout(function(){reloadItems()}, delay);
}

function voteNewitem(itemId) {
	if(selected_newitem != itemId) {
		$('#vote_but'+selected_newitem).css('color', 'white');
		$('#vote_but'+itemId).css('color', '#3399ff');
		selected_newitem = itemId;
		$('#3p_foot').html("<tr><td colspan='2'>" +
				"<input type='button' class='votebutton' onclick="+pick+"voteNewItemFin('true','"+itemId+"')"+pick+" value='Up' />" +
				"<input type='button' class='votebutton' onclick="+pick+"voteNewItemFin('false','"+itemId+"')"+pick+" value='Down' /></td></tr>");
	}
	else {
		$('#vote_but'+itemId).css('color', 'white');
		$('#3p_foot').html(""); 
		selected_newitem = null;
	}
}

function voteNewItemFin(value,itemId) {
	$.ajax({
		url : 'voteNewitem/'+value+'/'+userId+'/'+itemId,
		success : function(data) {
			if(data) {
				//SIKERES SZAVAZÁS VISSZAJELZÉSE
			}
		}
	});
	$('#3p_foot').html("");
	selected_newitem = null;
	setTimeout(function(){reloadNewitems()}, delay);
	setTimeout(function(){reloadItems()}, delay);
}

function suggestItem() {
	var number = $('#newitemprize').val();
	if(number.match(num_reg)) {
		$.ajax({
			url : 'suggestNewitem/'+$('#newitemname').val()+'/'+$('#newitemdescription').val()+'/'+number+'/'+userId,
			success : function(data) {
				if(data) {
					//SIKERES ÚJ ITEM VISSZAJELZÉSE
				}
			}
		});
		$('#3p_foot').html("");
		$("#third_panel_button").text('Suggest new item');
		$("#third_panel_button").val('Suggest new item');
		setTimeout(function(){reloadNewitems()}, delay);
	}
	else {
		$('#newitemprize').val(0);
	}
}

/************************HOVER*DESCRIPTION***************************/
function newitemDescription(newitemDesc) {
	if(selected_newitem==null) {
		$('#3p_foot').html("<div class='fancy_discription'>"+newitemDesc+"</div>");
	}
}

function newitemOut() {
	if(selected_newitem==null) {
		$('#3p_foot').text("");
	}
}

function auctionDescription(auctionDesc) {
	if(selected_bid == null) {
		$('#2p_foot').html("<div class='fancy_discription'>"+auctionDesc+"</div>");
	}
}

function auctionOut() {
	if(selected_bid == null) {
		$('#2p_foot').text("");
	}
}

function itemDescription(auctionDesc) {
	if(selected_auction==null) {
		$('#1p_foot').html("<div class='fancy_discription'>"+auctionDesc+"</div>");
	}
}

function itemOut() {
	if(selected_auction==null) {
		$('#1p_foot').text("");
	}
}

/***************************/
function reloadNewitems(){
	$.ajax({
		url : 'othernewitems/'.concat(userId),
		success : function(data) {
			$('#3p_body').html(data);
		}
	});
}

function reloadUserAuctions(){
	$.ajax({
		url : 'userauctions/'.concat(userId),
		success : function(data) {
			$('#2p_body').html(data);
		}
	});
}

function reloadOtherAuctions(){
	$.ajax({
		url : 'otherauctions/'.concat(userId),
		success : function(data) {
			$('#2p_body').html(data);
		}
	});
}

function reloadItems() {
	$.ajax({
		url : 'useritems/'.concat(userId),
		success : function(data) {
			$('#1p_body').html(data);
		}
	});
}



